package com.example.AlertMonitoringService.Service;

import com.example.AlertMonitoringService.Repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;
}
