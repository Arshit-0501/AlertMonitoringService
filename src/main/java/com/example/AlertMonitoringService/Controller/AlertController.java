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
        return "Alert Management System - OYO-AMS - Technodisrupters!!";
    }

    @RequestMapping("/dashboard")
    String dashboard() {
        return "Alert Management System Dashboard- OYO-AMS Dashboard- Technodisrupters!!";
    }

    @RequestMapping("/report")
    String report() {
        return "Alert Management System Report- OYO-AMS Report- Technodisrupters!!";
    }

    //create alerts in database from grafana
    @PostMapping(value = "/dashboard/alert")
    public void createAlert(@RequestBody AlertRequest alertRequest) {
        alertService.createAlert(alertRequest);
    }

    // Not required in phase 1 - created for personal testing
    @GetMapping(value = "/dashboard/alert/{id}")
    public AlertResponseItem getAlertById(@PathVariable Long id) {
         return alertService.getAlertById(id);
    }

    // Not required in phase 1 - created for personal testing
    @GetMapping(value = "/dashboard/alerts")
    public AlertResponse getAllAlert() {
        return alertService.getAllAlerts();
    }

    //Get alerts by alertStatus
    @GetMapping(value = "/dashboard/alerts/status/{status}")
    public AlertResponse getAlertsByStatus(@PathVariable String status) {
        return alertService.getAlertsByStatus(status);
    }

    //change alert status
    @PutMapping(value = "/dashboard/alert/{id}")
    public AlertResponseItem updateAlertStatus(@RequestBody AlertRequest alertRequest, @PathVariable Long id){
        return alertService.updateAlertStatus(alertRequest, id);
    }

    //Get Report Alerts By Filters - By team
    @GetMapping(value = "/report/filter/1/{teamId}")
    public AlertResponse getAlertsByFilters_TeamIdAndBetweenDates(@PathVariable("teamId") Long teamId) throws Exception {
        return alertService.getAlertsByFilters_TeamId(teamId);
    }

    //Get Report Alerts By Filters - Between dates
    @GetMapping(value = "/report/filter/2/{startDate}/{endDate}")
    public AlertResponse getAlertsByFilters_TeamIdAndBetweenDates(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Exception {
        return alertService.getAlertsByFilters_BetweenDates(startDate, endDate);
    }

    //Get Report Alerts By Filters - By team and between dates
    @GetMapping(value = "/report/filter/3/{teamId}/{startDate}/{endDate}")
    public AlertResponse getAlertsByFilters_TeamIdAndBetweenDates(@PathVariable("teamId") Long teamId, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Exception {
        return alertService.getAlertsByFilters_TeamIdAndBetweenDates(teamId, startDate, endDate);
    }
}

/*
For Frontend Team -

Get Alerts By Alert Status
oyoams/api/dashboard/alerts/status/{status}
{status} = Triggered/Acknowledged/Resolved

Update Alert Status
oyoams/api/dashboard/alert/{id}
{id} = alert Id

Get Report Alerts By Filters - By Team Id
oyoams/api/report/filter/1/{teamId}

Get Report Alerts By Filters - Between Dates
oyoams/api/report/filter/2/{startDate}/{endDate}

Get Report Alerts By Filters - By Team Id and Between Dates
oyoams/api/report/filter/3/{teamId}/{startDate}/{endDate}

{id} = filter id
id = 1 => by team id
id = 2 => by between two dates
id = 3 => by team id and between two dates

startDate = "YYYY-MM-DD"
endDate = "YYYY-MM-DD"
teamId = Long
 */