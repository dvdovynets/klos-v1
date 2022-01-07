package com.springboot.klos.dto.response;

import lombok.Data;

@Data
public class EventResponseDto {
    private Long id;
    private String eventName;
    private String eventDate;
}
