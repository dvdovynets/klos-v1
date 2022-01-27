package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventMapperTest {
    @InjectMocks
    private EventMapper eventMapper;
    private EventRequestDto requestDto;
    private EventResponseDto responseDto;
    private Event event;

    @BeforeEach
    void setUp() {
        requestDto = new EventRequestDto();
        requestDto.setEventName("KLOS-22");
        requestDto.setEventDate("11.11.2022");

        responseDto = new EventResponseDto();
        responseDto.setId(1L);
        responseDto.setEventName("KLOS-22");
        responseDto.setEventDate("11.11.2022");

        event = new Event();
        event.setEventDate(LocalDate.of(2022,11, 11));
        event.setEventName("KLOS-22");
    }

    @Test
    void mapToModel() {
        Event mappedEvent = eventMapper.mapToModel(requestDto);
        assertEquals(event, mappedEvent);
    }

    @Test
    void mapToDto() {
        event.setId(1L);
        EventResponseDto mappedResponse = eventMapper.mapToDto(event);
        assertEquals(responseDto, mappedResponse);
    }

    @Test
    void mapDataToEvent() {
        Event mappedEvent = eventMapper.mapDataToEvent(requestDto, new Event());
        assertEquals(event, mappedEvent);
    }
}