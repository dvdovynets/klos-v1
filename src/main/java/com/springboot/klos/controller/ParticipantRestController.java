package com.springboot.klos.controller;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.service.ParticipantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(description = "All operations for Participant entity except creation")
@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantRestController {
    private final ParticipantService participantService;

    public ParticipantRestController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @ApiOperation(value = "Endpoint for getting all participants", notes = "Access level USER.")
    @ApiParam(value = "Get method for Participants")
    @GetMapping
    public List<ParticipantResponseDto> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @ApiOperation(value = "Endpoint for getting participant by id", notes = "Access level USER.")
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> getParticipantById(
            @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    @ApiOperation(value = "Endpoint for updating participant", notes = "Access level USER.")
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponseDto> updateParticipant(
            @Valid @RequestBody ParticipantRequestDto dto, @PathVariable Long id) {
        return new ResponseEntity<>(participantService.updateParticipant(dto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint for soft deleting a participant", notes = "Access level USER.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable(name = "id") Long id) {
        participantService.deleteParticipant(id);
        return new ResponseEntity<>("Participant was successfully deleted!", HttpStatus.OK);
    }
}
