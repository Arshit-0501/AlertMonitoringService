package com.example.AlertMonitoringService.Repository;

import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {

    Page<Alert> findAll(Pageable pageable);

    List<Alert> findAllByAlertStatus(AlertStatus alertStatus);

    List<Alert> findAllByAlertStatus(Pageable pageable, AlertStatus alertStatus);

    List<Alert> findAllByTriggeredDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Alert> findAllByAlertStatusAndTriggeredDateTimeBetween(AlertStatus triggered, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Alert> findAllByAlertStatusAndAcknowledgedDateTimeBetween(AlertStatus acknowledged, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Alert> findAllByAlertStatusAndResolvedDateTimeBetween(AlertStatus resolved, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Alert> findByRuleIdAndAlertStatus(Pageable pageRequest, Long ruleId, AlertStatus alertStatus);

}
