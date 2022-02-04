package com.example.AlertMonitoringService.Service;

import com.example.AlertMonitoringService.DTO.AlertRequest;
import com.example.AlertMonitoringService.DTO.AlertResponse;
import com.example.AlertMonitoringService.DTO.AlertResponseItem;
import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import com.example.AlertMonitoringService.Repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public void createAlert(AlertRequest alertRequest) {
        Alert alert = Alert.builder()
                .alertName(alertRequest.getAlertName())
                .firedDateTime(LocalDateTime.now())
                .description(alertRequest.getDescription())
                .alertStatus(AlertStatus.Triggered)
                .build();

        alertRepository.save(alert);
    }

    public AlertResponseItem getAlertByID(Long id) {

        Alert alert = alertRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AlertId is Invalid !!"));
        AlertResponseItem alertResponseItem = AlertResponseItem.builder()
                .alertId(alert.getAlertId())
                .alertName(alert.getAlertName())
                .firedDateTime(alert.getFiredDateTime())
                .acknowledgedDateTime(alert.getAcknowledgedDateTime())
                .resolvedDateTime(alert.getResolvedDateTime())
                .description(alert.getDescription())
                .alertStatus(alert.getAlertStatus().name())
                .build();
        return alertResponseItem;
    }

    public AlertResponse getAllAlert() {
        List<Alert> alerts = alertRepository.findAll();
        List<AlertResponseItem> alertResponseList = new ArrayList<>();

        for(Alert alert: alerts) {
            AlertResponseItem alertResponseItem = AlertResponseItem.builder()
                    .alertId(alert.getAlertId())
                    .alertName(alert.getAlertName())
                    .firedDateTime(alert.getFiredDateTime())
                    .acknowledgedDateTime(alert.getAcknowledgedDateTime())
                    .resolvedDateTime(alert.getResolvedDateTime())
                    .description(alert.getDescription())
                    .alertStatus(alert.getAlertStatus().name())
                    .build();
            alertResponseList.add(alertResponseItem);
        }

        AlertResponse alertResponse = AlertResponse.builder()
                .alertResponseItemList(alertResponseList)
                .build();

        return alertResponse;
    }

    public AlertResponse getAlertByStatus(String status) {
        List<Alert> alerts = alertRepository.findByAlertStatus(AlertStatus.valueOf(status));
        List<AlertResponseItem> alertResponseList = new ArrayList<>();

        for (Alert alert : alerts) {
            AlertResponseItem alertResponseItem = AlertResponseItem.builder()
                    .alertId(alert.getAlertId())
                    .alertName(alert.getAlertName())
                    .firedDateTime(alert.getFiredDateTime())
                    .acknowledgedDateTime(alert.getAcknowledgedDateTime())
                    .resolvedDateTime(alert.getResolvedDateTime())
                    .description(alert.getDescription())
                    .alertStatus(alert.getAlertStatus().name())
                    .build();
            alertResponseList.add(alertResponseItem);
        }

        AlertResponse alertResponse = AlertResponse.builder()
                .alertResponseItemList(alertResponseList)
                .build();

        return alertResponse;

    }
}
