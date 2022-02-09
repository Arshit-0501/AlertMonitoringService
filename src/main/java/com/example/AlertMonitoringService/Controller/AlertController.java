package com.example.AlertMonitoringService.Controller;

import com.example.AlertMonitoringService.DTO.AlertFilterResponse;
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
        return "Alert Management System - OYO-AMS - Technodisrupters!!";
    }

    @RequestMapping("/report")
    String report() {
        return "Alert Management System Report- OYO-AMS Report- Technodisrupters!!";
    }

    //Create alerts in database from grafana
    @PostMapping(value = "/dashboard/alert")
    public void createAlert(@RequestBody AlertRequest alertRequest) throws Exception {
        alertService.createAlert(alertRequest);
    }

    //Get alerts by alertStatus
    @CrossOrigin
    @GetMapping(value = "/dashboard")
    public AlertResponse getAlertsByStatus(@RequestParam("status") String status, @RequestParam("team") String team, @RequestParam("pageNumber") Long pageNumber, @RequestParam("sortDirection") String sortDirection) {
        return alertService.getAlertsByStatus(status, team, pageNumber, sortDirection);
    }

    //Change alert status
    @CrossOrigin
    @PutMapping(value = "/dashboard")
    public AlertResponseItem updateAlertStatus(@RequestBody AlertRequest alertRequest, @RequestParam("alertId") Long id){
        return alertService.updateAlertStatus(alertRequest, id);
    }

    //Get Report Alerts By Filters - Between dates
    @CrossOrigin
    @GetMapping(value = "/report/filter")
    public AlertFilterResponse getAlertsByFilters(@RequestParam("status") String status, @RequestParam("team") String team, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("viewOption") String viewOption) throws Exception {
        return alertService.getAlertsByFilters(status, team, startDate, endDate, viewOption);
    }
}