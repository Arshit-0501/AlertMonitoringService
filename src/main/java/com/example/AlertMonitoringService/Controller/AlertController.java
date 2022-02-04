package com.example.AlertMonitoringService.Controller;

import com.example.AlertMonitoringService.DTO.AlertRequest;
import com.example.AlertMonitoringService.DTO.AlertResponse;
import com.example.AlertMonitoringService.DTO.AlertResponseItem;
import com.example.AlertMonitoringService.Service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlertController {

    @Autowired
    private AlertService alertService;

    @RequestMapping("/")
    String home() {
        return "Hello!";
    }

    @PostMapping(value = "/alert")
    public void createAlert(@RequestBody AlertRequest alertRequest) {
        alertService.createAlert(alertRequest);
    }

    @GetMapping(value = "/alert/{id}")
    public AlertResponseItem getAlertByID(@PathVariable Long id) {
         return alertService.getAlertByID(id);
    }

    @GetMapping(value = "alert")
    public AlertResponse getAllAlert() {
        return alertService.getAllAlert();
    }

    @GetMapping(value = "/alert/status/{status}")
    public AlertResponse getAlertByStatus(@PathVariable String status) {
        return alertService.getAlertByStatus(status);
    }
}
