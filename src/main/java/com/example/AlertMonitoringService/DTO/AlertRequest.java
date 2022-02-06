package com.example.AlertMonitoringService.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertRequest {

    @JsonProperty(value = "title")
    private String alertName;

    @JsonProperty(value = "message")
    private String description;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "startDate")
    private String startDate;

    @JsonProperty(value = "endDate")
    private String endDate;

    @JsonProperty(value = "teamId")
    private Long teamId;
}
