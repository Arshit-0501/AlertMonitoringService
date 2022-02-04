package com.example.AlertMonitoringService.Controller;

import com.example.AlertMonitoringService.Service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {

    @Autowired
    private AlertService alertService;

    @RequestMapping("/")
    String home() {
        return "Hello!";
    }
}
