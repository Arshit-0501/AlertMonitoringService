package com.example.AlertMonitoringService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertResponse {

    @JsonProperty(value = "alerts")
    List<AlertResponseItem> alertResponseItemList;
}
