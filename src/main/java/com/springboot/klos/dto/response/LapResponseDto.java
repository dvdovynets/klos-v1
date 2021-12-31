package com.springboot.klos.dto.response;

import lombok.Data;

@Data
public class LapResponseDto {
    private Long id;
    private int lapNumber;
    private String lapTime;
    private String actualTime;
    private String scannerId;
    private Long participantId;
}
