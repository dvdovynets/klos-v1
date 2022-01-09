package com.springboot.klos.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ResultRequestDto {
    @NotEmpty(message = "Status must not be empty")
    private String status;

    @NotEmpty(message = "Participant is must not be empty")
    private Long participantId;

    @NotEmpty(message = "Event id must not be empty")
    private Long eventId;
}
