package com.example.AlertMonitoringService.Repository;

import com.example.AlertMonitoringService.Model.Alert;
import com.example.AlertMonitoringService.Model.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {
    List<Alert> findAllByAlertStatus(AlertStatus alertStatus);

    List<Alert> findAllByTeamId(Long teamId);

    List<Alert> findAllByTriggeredDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Alert> findAllByTeamIdAndTriggeredDateTimeBetween(Long teamId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
