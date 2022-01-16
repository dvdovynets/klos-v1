package com.springboot.klos.exception;

import org.springframework.http.HttpStatus;

public class KLOSApiException extends RuntimeException {
    private final HttpStatus status;
    private String message;

    public KLOSApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
