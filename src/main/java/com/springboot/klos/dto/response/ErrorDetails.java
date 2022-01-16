package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(description = "Error details as response for the client")
@Data
public class ErrorDetails {
    @ApiModelProperty(value = "Date and time when error occurred")
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "Error message")
    private String message;

    @ApiModelProperty(value = "Additional details")
    private String details;
}
