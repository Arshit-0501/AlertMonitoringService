package com.example.AlertMonitoringService.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertFilterResponseItem {

    @JsonProperty(value = "date")
    String date;

    @JsonProperty(value = "allCount")
    Long allCount;

    @JsonProperty(value = "triggeredCount")
    Long triggeredCount;

    @JsonProperty(value = "acknowledgedCount")
    Long acknowledgedCount;

    @JsonProperty(value = "resolvedCount")
    Long resolvedCount;

    @JsonProperty(value = "teamCount")
    Long teamCount;
}
