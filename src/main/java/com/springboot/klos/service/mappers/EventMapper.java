package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.model.Event;
import com.springboot.klos.utils.DateTimePatternUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EventMapper implements GenericMapper<Event, EventRequestDto, EventResponseDto> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.DATE_PATTERN);

    @Override
    public Event mapToModel(EventRequestDto dto) {
        Event event = new Event();
        event.setEventName(dto.getEventName());
        event.setEventDate(LocalDate.parse(dto.getEventDate(), formatter));
        return event;
    }

    @Override
    public EventResponseDto mapToDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setEventName(event.getEventName());
        dto.setEventDate(event.getEventDate().format(formatter));
        return dto;
    }

    public Event mapDataToEvent(EventRequestDto dto, Event event) {
        event.setEventName(dto.getEventName());
        event.setEventDate(LocalDate.parse(dto.getEventDate(), formatter));
        return event;
    }
}
