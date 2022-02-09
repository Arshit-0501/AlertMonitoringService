package com.example.AlertMonitoringService.Service;

import com.example.AlertMonitoringService.DTO.*;
import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import com.example.AlertMonitoringService.Repository.AlertRepository;
import com.example.AlertMonitoringService.Utility.AlertServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertServiceUtility alertServiceUtility;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void createAlert(AlertRequest alertRequest) throws Exception {
        String stateString = alertRequest.getState().toLowerCase();
        if(!stateString.contains("ok")) {
            Alert alert = alertServiceUtility.alertRequestToAlertUtil(alertRequest);
            alertRepository.save(alert);
        } else {
            //resolved alert
            Pageable pageRequest = PageRequest.of(0, 1, Sort.by("triggeredDateTime").and(Sort.by("alertId")));
            Page<Alert> pageAlert = alertRepository.findByRuleIdAndAlertStatus(pageRequest, alertRequest.getRuleId(), AlertStatus.Triggered);
            List<Alert> alerts = pageAlert.getContent();
            if(alerts.size()==0) {
                throw new Exception("No alert found!!");
            }
            Alert alert = alerts.get(0);
            alert.setAlertStatus(AlertStatus.Resolved);
            alert.setResolvedDateTime(LocalDateTime.parse(LocalDateTime.now().format(dateFormatter), dateFormatter));
            alertRepository.save(alert);
        }
    }

    public AlertResponse getAlertsByStatus(String status, String team, Long pageNumber, String sortDirection) {
        Pageable pageRequest;
        if(Objects.equals(sortDirection, "desc")) {
            pageRequest = PageRequest.of((int) (pageNumber - 1), 10, Sort.by("triggeredDateTime").descending().and(Sort.by("alertId")));
        }else {
            pageRequest = PageRequest.of((int) (pageNumber-1),10, Sort.by("triggeredDateTime").and(Sort.by("alertId")));
        }
        List<Alert> alerts;
        if (!Objects.equals(status, "All")) {
            alerts = alertRepository.findAllByAlertStatus(pageRequest,AlertStatus.valueOf(status));
        } else {
            Page<Alert> page = alertRepository.findAll(pageRequest);
            alerts = page.getContent();
        }
        if(Objects.equals(team, "All")) {
            return alertServiceUtility.alertsToAlertResponseUtil(alerts);
        }
        List<Alert> teamAlerts = new ArrayList<>();
        team = team.toLowerCase();
        for(Alert alert : alerts) {
            String message = alert.getDescription().toLowerCase();
            if(message.contains(team)) {
                teamAlerts.add(alert);
            }
        }
        return alertServiceUtility.alertsToAlertResponseUtil(teamAlerts);
    }

    public AlertResponseItem updateAlertStatus(AlertRequest alertRequest, Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AlertId is Invalid !!"));
        if(!alertServiceUtility.updateAlertStatusValidityUtil(alert, alertRequest)) {
            throw new EntityNotFoundException("Invalid Update Request!!");
        }
        alert.setAlertStatus(AlertStatus.valueOf(alertRequest.getStatus()));
        alert = alertServiceUtility.updateAlertDateTimeUtil(alert);
        if(alert.getAlertStatus()==AlertStatus.Resolved) {
            alert.setResolvedReason(alertRequest.getResolvedReason());
        }
        alertRepository.save(alert);
        return alertServiceUtility.alertToAlertResponseItemUtil(alert);
    }

    public AlertFilterResponse getAlertsByFilters(String status, String team, String startDate, String endDate, String viewOption) {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00", dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59", dateFormatter);
        List<AlertFilterResponseItem> alertFilterResponseItemList = new ArrayList<>();
        if(Objects.equals(viewOption, "day")) {
            for (LocalDateTime date = startDateTime; date.isBefore(endDateTime); date = date.plusDays(1)) {
                long[] count = getAlertsByFilterDate(status, team, date, date.plusDays(1));
                AlertFilterResponseItem alertFilterResponseItem = AlertFilterResponseItem.builder()
                        .date(date.format(dateFormatter).substring(0, 10))
                        .allCount(count[0])
                        .triggeredCount(count[1])
                        .acknowledgedCount(count[2])
                        .resolvedCount(count[3])
                        .teamCount(count[4])
                        .build();
                alertFilterResponseItemList.add(alertFilterResponseItem);
            }
        } else if(Objects.equals(viewOption, "week")) {
            for (LocalDateTime date = startDateTime; date.isBefore(endDateTime); date = date.plusDays(7)) {
                long[] count = getAlertsByFilterDate(status, team, date, date.plusDays(7));
                AlertFilterResponseItem alertFilterResponseItem = AlertFilterResponseItem.builder()
                        .date(date.format(dateFormatter).substring(0, 10))
                        .allCount(count[0])
                        .triggeredCount(count[1])
                        .acknowledgedCount(count[2])
                        .resolvedCount(count[3])
                        .teamCount(count[4])
                        .build();
                alertFilterResponseItemList.add(alertFilterResponseItem);
            }
        }else {
            for (LocalDateTime date = startDateTime; date.isBefore(endDateTime); date = date.plusMonths(1)) {
                long[] count = getAlertsByFilterDate(status, team, date, date.plusMonths(1));
                AlertFilterResponseItem alertFilterResponseItem = AlertFilterResponseItem.builder()
                        .date(date.format(dateFormatter).substring(0, 10))
                        .allCount(count[0])
                        .triggeredCount(count[1])
                        .acknowledgedCount(count[2])
                        .resolvedCount(count[3])
                        .teamCount(count[4])
                        .build();
                alertFilterResponseItemList.add(alertFilterResponseItem);
            }
        }
        return AlertFilterResponse.builder()
                .alertFilterResponseItemList(alertFilterResponseItemList)
                .build();
    }

    private long[] getAlertsByFilterDate(String status, String team, LocalDateTime startDateTime, LocalDateTime endDateTime){
        List<Alert> allAlerts = new ArrayList<>();
        List<Alert> triggeredAlerts = new ArrayList<>();
        List<Alert> acknowledgedAlerts = new ArrayList<>();
        List<Alert> resolvedAlerts = new ArrayList<>();

        allAlerts = alertRepository.findAllByTriggeredDateTimeBetween(startDateTime, endDateTime);
        triggeredAlerts = alertRepository.findAllByAlertStatusAndTriggeredDateTimeBetween(AlertStatus.Triggered, startDateTime, endDateTime);
        acknowledgedAlerts = alertRepository.findAllByAlertStatusAndAcknowledgedDateTimeBetween(AlertStatus.Acknowledged, startDateTime, endDateTime);
        resolvedAlerts = alertRepository.findAllByAlertStatusAndResolvedDateTimeBetween(AlertStatus.Resolved, startDateTime, endDateTime);

        long[] count = {(long) allAlerts.size(),0,0,0,0};

        if(Objects.equals(team, "All")) {
            count[1] = (long)triggeredAlerts.size();
            count[2] = (long)acknowledgedAlerts.size();
            count[3] = (long)resolvedAlerts.size();
            count[4] = count[1] + count[2] + count[3];
            return count;
        }

        team = team.toLowerCase();
        for(Alert alert : triggeredAlerts) {
            String message = alert.getDescription().toLowerCase();
            if(message.contains(team))
                count[1]++;
        }
        for(Alert alert : acknowledgedAlerts) {
            String message = alert.getDescription().toLowerCase();
            if(message.contains(team))
                count[2]++;
        }
        for(Alert alert : resolvedAlerts) {
            String message = alert.getDescription().toLowerCase();
            if(message.contains(team))
                count[3]++;
        }
        count[4] = count[1] + count[2] + count[3];

        return count;
    }

}


