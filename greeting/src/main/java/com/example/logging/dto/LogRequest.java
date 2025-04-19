package com.example.logging.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequest {
    private String message;

    public LogRequest() {}

    public LogRequest(String message) {
        this.message = message;
    }

}