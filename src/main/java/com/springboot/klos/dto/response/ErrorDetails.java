package com.springboot.klos.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
