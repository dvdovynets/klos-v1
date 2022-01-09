package com.springboot.klos.controller;

import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRestController {
    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto dto) {
        return new ResponseEntity<>(eventService.createEvent(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<EventResponseDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @Valid @RequestBody EventRequestDto dto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(eventService.updateEvent(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "id") Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>("Event was successfully deleted!", HttpStatus.OK);
    }
}
