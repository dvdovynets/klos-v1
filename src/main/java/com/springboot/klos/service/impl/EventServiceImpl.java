package com.springboot.klos.service.impl;

import com.springboot.klos.dao.EventDao;
import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Event;
import com.springboot.klos.service.EventService;
import com.springboot.klos.service.mappers.EventMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventDao eventDao,
                            EventMapper eventMapper) {
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    @Override
    public EventResponseDto createEvent(EventRequestDto dto) {
        return eventMapper.mapToDto(eventDao.save(eventMapper.mapToModel(dto)));
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        return eventDao.findAll().stream()
                .filter(event -> !event.isDeleted())
                .map(eventMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto getEventById(Long id) {
        Event event = eventDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Event", "id", id.toString()));
        return eventMapper.mapToDto(event);
    }

    @Override
    public EventResponseDto updateEvent(EventRequestDto dto, Long id) {
        Event event = eventDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Event", "id", id.toString()));
        event = eventMapper.mapDataToEvent(dto, event);
        return eventMapper.mapToDto(eventDao.save(event));
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Event", "id", id.toString()));
        event.setDeleted(true);
        eventDao.save(event);
    }
}
