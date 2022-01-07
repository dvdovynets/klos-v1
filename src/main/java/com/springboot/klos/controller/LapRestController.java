package com.springboot.klos.controller;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.service.LapService;
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

import java.util.List;

@RestController
@RequestMapping("/api/laps")
public class LapRestController {
    private final LapService lapService;

    public LapRestController(LapService lapService) {
        this.lapService = lapService;
    }

    @PostMapping
    public ResponseEntity<LapResponseDto> createLap(@RequestBody LapRequestDto dto) {
        return new ResponseEntity<>(lapService.createLap(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<LapResponseDto> getAllLaps() {
        return lapService.getAllLaps();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LapResponseDto> getLapById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(lapService.getLapById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LapResponseDto> updateLap(
            @RequestBody LapRequestDto dto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(lapService.updateLap(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLap(@PathVariable(name = "id") Long id) {
        lapService.deleteLap(id);
        return new ResponseEntity<>("Lap was successfully deleted!", HttpStatus.OK);
    }
}
