package com.example.AlertMonitoringService.Utility;

import com.example.AlertMonitoringService.DTO.AlertRequest;
import com.example.AlertMonitoringService.DTO.AlertResponse;
import com.example.AlertMonitoringService.DTO.AlertResponseItem;
import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertServiceUtility {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    //Utility function to convert AlertRequest to Alert
    public Alert alertRequestToAlertUtil(AlertRequest alertRequest) {
        return Alert.builder()
                .alertName(alertRequest.getAlertName())
                .triggeredDateTime(LocalDateTime.parse(LocalDateTime.now().format(dateFormatter), dateFormatter))
                .description(alertRequest.getDescription())
                .alertStatus(AlertStatus.Triggered)
                .ruleId(alertRequest.getRuleId())
                .state(alertRequest.getState())
                .resolvedReason(alertRequest.getResolvedReason())
                .build();
    }

    //Utility function to convert an alert to AlertResponseItem
    public AlertResponseItem alertToAlertResponseItemUtil(Alert alert) {
        return AlertResponseItem.builder()
                .alertId(alert.getAlertId())
                .alertName(alert.getAlertName())
                .triggeredDateTime(String.valueOf(alert.getTriggeredDateTime()))
                .acknowledgedDateTime(String.valueOf(alert.getAcknowledgedDateTime()))
                .resolvedDateTime(String.valueOf(alert.getResolvedDateTime()))
                .description(alert.getDescription())
                .alertStatus(String.valueOf(alert.getAlertStatus()))
                .state(alert.getState())
                .ruleId(alert.getRuleId())
                .resolvedReason(alert.getResolvedReason())
                .build();
    }

    //Utility function to convert alerts to AlertResponse
    public AlertResponse alertsToAlertResponseUtil(List<Alert> alerts, long triggeredCount, long acknowledgedCount, long resolvedCount) {
        List<AlertResponseItem> alertResponseList = new ArrayList<>();
        for (Alert alert : alerts) {
            AlertResponseItem alertResponseItem = alertToAlertResponseItemUtil(alert);
            alertResponseList.add(alertResponseItem);
        }
        return AlertResponse.builder()
                .alertResponseItemList(alertResponseList)
                .triggeredCount(triggeredCount)
                .acknowledgedCount(acknowledgedCount)
                .resolvedCount(resolvedCount)
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
        return (alert.getAlertStatus() == AlertStatus.Triggered && AlertStatus.valueOf(alertRequest.getStatus()) == AlertStatus.Acknowledged)
        || (alert.getAlertStatus() == AlertStatus.Acknowledged && AlertStatus.valueOf(alertRequest.getStatus()) == AlertStatus.Resolved);
    }

}
