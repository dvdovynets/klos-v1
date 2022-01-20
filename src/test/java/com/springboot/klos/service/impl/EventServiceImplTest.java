package com.springboot.klos.service.impl;

import com.springboot.klos.dao.EventDao;
import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.model.Event;
import com.springboot.klos.service.mappers.EventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    private static final String EVENT_NAME = "KLOS-2022";
    private static final String EVENT_DATE_DTO = "11.11.2022";
    private static final LocalDate EVENT_DATE = LocalDate.of(2022, 11, 11);
    @InjectMocks
    private EventServiceImpl eventService;
    @Mock
    private EventDao eventDao;
    @Mock
    private EventMapper eventMapper;

    @Test
    void createEvent() {
        EventRequestDto requestDto = new EventRequestDto();
        requestDto.setEventName(EVENT_NAME);
        requestDto.setEventDate(EVENT_DATE_DTO);

        Event event = new Event();
        event.setEventName(EVENT_NAME);
        event.setEventDate(EVENT_DATE);

        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setId(1L);
        responseDto.setEventName(EVENT_NAME);
        responseDto.setEventDate(EVENT_DATE_DTO);

        when(eventMapper.mapToModel(requestDto)).thenReturn(event);
        when(eventMapper.mapToDto(any())).thenReturn(responseDto);
        when(eventDao.save(any())).thenReturn(event);

        EventResponseDto eventFromDb = eventService.createEvent(requestDto);

        assertEquals(responseDto, eventFromDb);

        verify(eventDao).save(any());
    }

    @Test
    void getAllEvents() {
        Event event = new Event();
        event.setId(1L);
        event.setEventName(EVENT_NAME);
        event.setEventDate(EVENT_DATE);

        List<Event> events = List.of(event);

        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setId(1L);
        responseDto.setEventName(EVENT_NAME);
        responseDto.setEventDate(EVENT_DATE_DTO);

        when(eventDao.findAll()).thenReturn(events);
        when(eventMapper.mapToDto(any())).thenReturn(responseDto);

        List<EventResponseDto> allEvents = eventService.getAllEvents();

        assertEquals(events.size(), allEvents.size());

        verify(eventDao).findAll();
    }

    @Test
    void getEventById() {
        Event event = new Event();
        event.setId(1L);
        event.setEventName(EVENT_NAME);
        event.setEventDate(EVENT_DATE);

        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setId(1L);
        responseDto.setEventName(EVENT_NAME);
        responseDto.setEventDate(EVENT_DATE_DTO);

        when(eventDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(event));
        when(eventMapper.mapToDto(event)).thenReturn(responseDto);

        EventResponseDto eventFromDb = eventService.getEventById(1L);

        assertEquals(responseDto, eventFromDb);

        verify(eventDao).findByIdAndIsDeleted(anyLong(), anyBoolean());
    }

    @Test
    void updateEvent() {
        EventRequestDto requestDto = new EventRequestDto();
        requestDto.setEventName(EVENT_NAME);
        requestDto.setEventDate(EVENT_DATE_DTO);

        Event event = new Event();
        event.setId(1L);
        event.setEventName(EVENT_NAME);
        event.setEventDate(EVENT_DATE);

        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setId(1L);
        responseDto.setEventName(EVENT_NAME);
        responseDto.setEventDate(EVENT_DATE_DTO);

        when(eventMapper.mapDataToEvent(requestDto, event)).thenReturn(event);
        when(eventMapper.mapToDto(event)).thenReturn(responseDto);
        when(eventDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(event));
        when(eventDao.save(any())).thenReturn(event);

        EventResponseDto eventFromDb = eventService.updateEvent(requestDto, 1L);

        assertEquals(responseDto, eventFromDb);

        verify(eventDao).save(any());
        verify(eventDao).findByIdAndIsDeleted(anyLong(), anyBoolean());
        verify(eventMapper).mapDataToEvent(any(), any());
        verify(eventMapper).mapToDto(any());
    }

    @Test
    void deleteEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setEventName(EVENT_NAME);
        event.setEventDate(EVENT_DATE);

        when(eventDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(event));

        eventService.deleteEvent(1L);

        verify(eventDao).save(event);
    }
}