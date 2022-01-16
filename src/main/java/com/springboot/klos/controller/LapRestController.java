package com.springboot.klos.controller;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.service.LapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(description = "All operation with Lap entity")
@RestController
@RequestMapping("/api/v1/laps")
public class LapRestController {
    private final LapService lapService;

    public LapRestController(LapService lapService) {
        this.lapService = lapService;
    }

    @ApiOperation(value = "Endpoint for adding new lap",
            notes = "Access level ADMIN. "
                    + "Also it will recalculate all statistic fields for related result.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LapResponseDto> createLap(@Valid @RequestBody LapRequestDto dto) {
        return new ResponseEntity<>(lapService.createLap(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint for getting all laps", notes = "Access level USER.")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<LapResponseDto> getAllLaps() {
        return lapService.getAllLaps();
    }

    @ApiOperation(value = "Endpoint for getting lap by id", notes = "Access level USER.")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<LapResponseDto> getLapById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(lapService.getLapById(id));
    }

    @ApiOperation(value = "Endpoint for getting all laps for related result",
            notes = "Access level USER.")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<LapResponseDto> updateLap(
            @Valid @RequestBody LapRequestDto dto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(lapService.updateLap(dto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint for deleting a lap", notes = "Access level ADMIN.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLap(@PathVariable(name = "id") Long id) {
        lapService.deleteLap(id);
        return new ResponseEntity<>("Lap was successfully deleted!", HttpStatus.OK);
    }
}
