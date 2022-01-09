package com.springboot.klos.controller;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/participants")
public class ParticipantRestController {
    private final ParticipantService participantService;

    public ParticipantRestController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ParticipantResponseDto> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> getParticipantById(
            @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> updateParticipant(
            @Valid @RequestBody ParticipantRequestDto dto, @PathVariable Long id) {
        return new ResponseEntity<>(participantService.updateParticipant(dto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable(name = "id") Long id) {
        participantService.deleteParticipant(id);
        return new ResponseEntity<>("Participant was successfully deleted!", HttpStatus.OK);
    }
}
