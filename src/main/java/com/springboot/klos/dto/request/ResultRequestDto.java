package com.springboot.klos.dto.request;

import lombok.Data;

@Data
public class ResultRequestDto {
    private String status;
    private Long participantId;
    private Long eventId;
}
