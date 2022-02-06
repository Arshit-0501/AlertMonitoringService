package com.example.AlertMonitoringService.Utility;

import com.example.AlertMonitoringService.DTO.AlertRequest;
import com.example.AlertMonitoringService.DTO.AlertResponse;
import com.example.AlertMonitoringService.DTO.AlertResponseItem;
import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AlertServiceUtility {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    //Utility function to convert AlertRequest to Alert
    public Alert alertRequestToAlertUtil(AlertRequest alertRequest) {
        return Alert.builder()
                .alertName(alertRequest.getAlertName())
                .triggeredDateTime(LocalDateTime.parse(LocalDateTime.now().format(dateFormatter), dateFormatter))
                .description(alertRequest.getDescription())
                .alertStatus(AlertStatus.Triggered)
                .teamId(alertRequest.getTeamId()!=null?alertRequest.getTeamId():1L)
                .build();
    }

    //Utility function to convert an alert to AlertResponseItem
    public AlertResponseItem alertToAlertResponseItemUtil(Alert alert) {
        return AlertResponseItem.builder()
                .alertId(alert.getAlertId())
                .alertName(alert.getAlertName())
                .triggeredDateTime(alert.getTriggeredDateTime())
                .acknowledgedDateTime(alert.getAcknowledgedDateTime())
                .resolvedDateTime(alert.getResolvedDateTime())
                .description(alert.getDescription())
                .alertStatus(alert.getAlertStatus().name())
                .teamId(alert.getTeamId())
                .build();
    }

    //Utility function to convert alerts to AlertResponse
    public AlertResponse alertsToAlertResponseUtil(List<Alert> alerts) {
        List<AlertResponseItem> alertResponseList = new ArrayList<>();
        for (Alert alert : alerts) {
            AlertResponseItem alertResponseItem = alertToAlertResponseItemUtil(alert);
            alertResponseList.add(alertResponseItem);
        }
        return AlertResponse.builder()
                .alertResponseItemList(alertResponseList)
                .build();
    }

    //Utility function to update alert date and time
    public Alert updateAlertDateTimeUtil(Alert alert) {
        if(alert.getAlertStatus() == AlertStatus.Acknowledged) {
            alert.setAcknowledgedDateTime(LocalDateTime.parse(LocalDateTime.now().format(dateFormatter), dateFormatter));
        }
        if(alert.getAlertStatus() == AlertStatus.Resolved) {
            alert.setResolvedDateTime(LocalDateTime.parse(LocalDateTime.now().format(dateFormatter), dateFormatter));
        }
        return alert;
    }

    //Utility function to check validity of alertStatus update request
    public boolean updateAlertStatusValidityUtil(Alert alert, AlertRequest alertRequest) {
        if(alert.getAlertStatus() == AlertStatus.Triggered && AlertStatus.valueOf(alertRequest.getStatus()) == AlertStatus.Acknowledged){
            return true;
        }
        if(alert.getAlertStatus() == AlertStatus.Acknowledged && AlertStatus.valueOf(alertRequest.getStatus()) == AlertStatus.Resolved){
            return true;
        }
        return false;
    }

    //Utility function to check validity for filters
    public boolean filtersValidityUtil(Long filterId, AlertRequest alertRequest) {
        if(filterId == 1) {
            return alertRequest.getTeamId() != null;
        }
        if(filterId == 2) {
            return alertRequest.getStartDate() != null && alertRequest.getEndDate() != null;
        }
        if(filterId == 3) {
            return alertRequest.getTeamId() != null && alertRequest.getStartDate() != null && alertRequest.getEndDate() != null;
        }
        return false;
    }

}
