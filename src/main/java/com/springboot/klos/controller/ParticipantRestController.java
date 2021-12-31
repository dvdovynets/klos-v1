package com.springboot.klos.controller;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantRestController {
    private final ParticipantService participantService;

    public ParticipantRestController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDto> createParticipant(@RequestBody ParticipantRequestDto requestDto) {
        return new ResponseEntity<>(participantService.createParticipant(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ParticipantResponseDto> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> getParticipantById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> partiallyUpdateParticipant(
            @RequestBody ParticipantRequestDto dto, @PathVariable Long id) {
        
    }
}
