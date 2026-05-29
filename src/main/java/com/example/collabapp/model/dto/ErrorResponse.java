package com.example.collabapp.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timeStamp;
}
