package com.example.AlertMonitoringService.DTO;

import com.example.AlertMonitoringService.Model.AlertStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDateTime triggeredDateTime;

    @JsonProperty(value="acknowledgedDateTime")
    private LocalDateTime acknowledgedDateTime;

    @JsonProperty(value="resolvedDateTime")
    private LocalDateTime resolvedDateTime;

    @JsonProperty(value="description")
    private String description;

    @JsonProperty(value="alertStatus")
    private String alertStatus;

    @JsonProperty(value = "teamId")
    private Long teamId;
}
