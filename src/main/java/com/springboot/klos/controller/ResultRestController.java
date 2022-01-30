package com.springboot.klos.controller;

import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.service.ResultService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(description = "All operations for Result entity")
@RestController
@RequestMapping("/api/v1/results")
public class ResultRestController {
    private final ResultService resultService;

    public ResultRestController(ResultService resultService) {
        this.resultService = resultService;
    }

    @ApiOperation(value = "Endpoint for registration for a new event(adding new result)",
            notes = "Access level USER.")
    @PostMapping
    public ResponseEntity<ResultResponseDto> createResult(
            @Valid @RequestBody ResultRequestDto dto) {
        return new ResponseEntity<>(resultService.createResult(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint for getting all results",
            notes = "Access level ANY. "
                    + "If an eventId will be added, endpoint will return results for "
                    + "a particular event.")
    @GetMapping
    public List<ResultResponseDto> getAllResults(
            @RequestParam(value = "eventId", defaultValue = "0", required = false) Long eventId) {
        return resultService.getAllResults(eventId);
    }

    @ApiOperation(value = "Endpoint for getting all laps for result",
            notes = "Access level ANY")
    @GetMapping("/{id}/laps")
    public List<LapResponseDto> getAllLapsForResult(@PathVariable(name = "id") Long id) {
        return resultService.getAllLapsForResult(id);
    }

    @ApiOperation(value = "Endpoint for getting result by id",
            notes = "Access level ANY")
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponseDto> getResultById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(resultService.getResultById(id));
    }

    @ApiOperation(value = "Endpoint for updating a result",
            notes = "Access level ADMIN.")
    @PutMapping("/{id}")
    public ResponseEntity<ResultResponseDto> updateResult(
            @Valid @RequestBody ResultRequestDto dto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(resultService.updateResult(dto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint for soft deleting a result",
            notes = "Access level ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResult(@PathVariable(name = "id") Long id) {
        resultService.deleteResult(id);
        return new ResponseEntity<>("Result was deleted successfully!", HttpStatus.OK);
    }
}
