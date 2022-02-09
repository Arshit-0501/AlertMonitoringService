package com.example.AlertMonitoringService.DTO;

import com.example.AlertMonitoringService.Model.AlertStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertResponseItem {

    @JsonProperty(value = "alertId")
    private Long alertId;

    @JsonProperty(value="alertName")
    private String alertName;

    @JsonProperty(value="triggeredDateTime")
    private String triggeredDateTime;

    @JsonProperty(value="acknowledgedDateTime")
    private String acknowledgedDateTime;

    @JsonProperty(value="resolvedDateTime")
    private String resolvedDateTime;

    @JsonProperty(value="description")
    private String description;

    @JsonProperty(value="alertStatus")
    private String alertStatus;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "ruleId")
    private Long ruleId;

    @JsonProperty(value = "resolvedReason")
    private String resolvedReason;

}
