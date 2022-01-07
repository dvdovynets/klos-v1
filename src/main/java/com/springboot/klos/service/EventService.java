package com.springboot.klos.service;

import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;

import java.util.List;

public interface EventService {
    EventResponseDto createEvent(EventRequestDto dto);

    List<EventResponseDto> getAllEvents();

    EventResponseDto getEventById(Long id);

    EventResponseDto updateEvent(EventRequestDto dto, Long id);

    void deleteEvent(Long id);
}
