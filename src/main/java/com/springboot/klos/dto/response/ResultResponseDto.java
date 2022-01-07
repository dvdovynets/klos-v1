package com.springboot.klos.dto.response;

import lombok.Data;

@Data
public class ResultResponseDto {
    private Long resultId;
    private String status;
    private String participantFullName;
    private String gender;
    private int lapsCompleted;
    private Double totalDistance;
    private String fastestLoop;
    private String slowestLoop;
    private String averageLoop;
    private String totalTimeRunning;
    private Long participantId;
    private Long eventId;
}
