package com.example.AlertMonitoringService.Repository;

import com.example.AlertMonitoringService.Model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {
}
