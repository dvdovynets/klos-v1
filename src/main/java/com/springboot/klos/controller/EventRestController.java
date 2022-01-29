package com.springboot.klos.controller;

import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(description = "All operations for Event entity")
@RestController
@RequestMapping("/api/v1/events")
public class EventRestController {
    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @ApiOperation(value = "Endpoint for adding new event", notes = "Access level ADMIN")
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto dto) {
        return new ResponseEntity<>(eventService.createEvent(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint for getting all events", notes = "Access level USER")
    @GetMapping
    public List<EventResponseDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @ApiOperation(value = "Endpoint for getting event by id", notes = "Access level USER")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @ApiOperation(value = "Endpoint for updating an event", notes = "Access level ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @Valid @RequestBody EventRequestDto dto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(eventService.updateEvent(dto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint for soft deleting an event", notes = "Access level ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "id") Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>("Event was deleted successfully!", HttpStatus.OK);
    }
}
