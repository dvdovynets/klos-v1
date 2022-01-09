package com.springboot.klos.controller;

import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultRestController {
    private final ResultService resultService;

    public ResultRestController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<ResultResponseDto> createResult(
            @Valid @RequestBody ResultRequestDto dto) {
        return new ResponseEntity<>(resultService.createResult(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ResultResponseDto> getAllResults(
            @RequestParam(value = "eventId", defaultValue = "0", required = false) Long eventId) {
        return resultService.getAllResults(eventId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponseDto> getResultById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(resultService.getResultById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponseDto> updateResult(
            @Valid @RequestBody ResultRequestDto dto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(resultService.updateResult(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResult(@PathVariable(name = "id") Long id) {
        resultService.deleteResult(id);
        return new ResponseEntity<>("Result was successfully deleted!", HttpStatus.OK);
    }
}
