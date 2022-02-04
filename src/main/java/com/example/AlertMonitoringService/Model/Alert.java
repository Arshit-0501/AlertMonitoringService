package com.example.AlertMonitoringService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "alerts")
public class Alert {

    @Id
    @Column(name = "alertId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertId;

    @Column(name="alertName",nullable = false)
    private String alertName;

    @Column(name="firedDateTime")
    private LocalDateTime firedDateTime;

    @Column(name="acknowledgedDateTime")
    private LocalDateTime acknowledgedDateTime;

    @Column(name="resolvedDateTime")
    private LocalDateTime resolvedDateTime;

    @Column(name="description")
    private String description;

    @Column(name="alertStatus")
    @Enumerated
    private AlertStatus alertStatus;

}
