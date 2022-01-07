package com.springboot.klos.dto.request;

import lombok.Data;

@Data
public class LapRequestDto {
    private int lapNumber;
    private String lapTime;
    private String actualTime;
    private String scannerId;
    private String braceletId;
    private Long eventId;
}
