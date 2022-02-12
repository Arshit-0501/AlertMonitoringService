package com.example.AlertMonitoringService.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlertInvalidUpdateException extends RuntimeException{
    public AlertInvalidUpdateException(String message) {
        super(message);
    }
}
