package com.example.AlertMonitoringService.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AlertIdNotFoundException extends RuntimeException{
    public AlertIdNotFoundException(String message) {
        super(message);
    }
}
