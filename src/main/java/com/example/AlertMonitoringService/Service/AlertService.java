package com.example.AlertMonitoringService.Service;

import com.example.AlertMonitoringService.DTO.AlertRequest;
import com.example.AlertMonitoringService.DTO.AlertResponse;
import com.example.AlertMonitoringService.DTO.AlertResponseItem;
import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import com.example.AlertMonitoringService.Repository.AlertRepository;
import com.example.AlertMonitoringService.Utility.AlertServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertServiceUtility alertServiceUtility;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void createAlert(AlertRequest alertRequest) {
        Alert alert = alertServiceUtility.alertRequestToAlertUtil(alertRequest);
        alertRepository.save(alert);
    }

    public AlertResponseItem getAlertById(Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AlertId is Invalid !!"));
        return alertServiceUtility.alertToAlertResponseItemUtil(alert);
    }

    public AlertResponse getAllAlerts() {
        List<Alert> alerts = alertRepository.findAll();
        return alertServiceUtility.alertsToAlertResponseUtil(alerts);
    }

    public AlertResponse getAlertsByStatus(String status) {
        List<Alert> alerts = alertRepository.findAllByAlertStatus(AlertStatus.valueOf(status));
        return alertServiceUtility.alertsToAlertResponseUtil(alerts);
    }

    public AlertResponseItem updateAlertStatus(AlertRequest alertRequest, Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AlertId is Invalid !!"));
        if(!alertServiceUtility.updateAlertStatusValidityUtil(alert, alertRequest)) {
            throw new EntityNotFoundException("Invalid Update Request!!");
        }
        alert.setAlertStatus(AlertStatus.valueOf(alertRequest.getStatus()));
        alert = alertServiceUtility.updateAlertDateTimeUtil(alert);
        alertRepository.save(alert);
        return alertServiceUtility.alertToAlertResponseItemUtil(alert);
    }

    private AlertResponse getAlertsByTeamId(AlertRequest alertRequest) {
        Long teamId = alertRequest.getTeamId();
        List<Alert> alerts = alertRepository.findAllByTeamId(teamId);
        return alertServiceUtility.alertsToAlertResponseUtil(alerts);
    }

    private AlertResponse getAlertsBetweenTwoDates(AlertRequest alertRequest) {
        LocalDateTime startDateTime = LocalDateTime.parse(alertRequest.getStartDate() + " 00:00", dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(alertRequest.getEndDate() + " 23:59", dateFormatter);
        List<Alert> alerts = alertRepository.findAllByTriggeredDateTimeBetween(startDateTime, endDateTime);
        return alertServiceUtility.alertsToAlertResponseUtil(alerts);
    }

    private AlertResponse getAlertsByTeamIdAndBetweenTwoDates(AlertRequest alertRequest) {
        Long teamId = alertRequest.getTeamId();
        LocalDateTime startDateTime = LocalDateTime.parse(alertRequest.getStartDate() + " 00:00", dateFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(alertRequest.getEndDate() + " 23:59", dateFormatter);
        List<Alert> alerts = alertRepository.findAllByTeamIdAndTriggeredDateTimeBetween(teamId, startDateTime, endDateTime);
        return alertServiceUtility.alertsToAlertResponseUtil(alerts);
    }

    public AlertResponse getAlertsByFilters(Long filterId, AlertRequest alertRequest) throws Exception {
        if(!alertServiceUtility.filtersValidityUtil(filterId, alertRequest)) {
            throw new Exception("Invalid Filter Parameters!!");
        }
        if (filterId == 1) {
            return getAlertsByTeamId(alertRequest);
        } else if (filterId == 2) {
            return getAlertsBetweenTwoDates(alertRequest);
        } else if (filterId == 3) {
            return getAlertsByTeamIdAndBetweenTwoDates(alertRequest);
        }
        return null;
    }
}
