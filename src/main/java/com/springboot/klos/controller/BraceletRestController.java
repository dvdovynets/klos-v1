package com.springboot.klos.controller;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.service.BraceletService;
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

@Api(description = "All operations for Bracelet entity")
@RestController
@RequestMapping("/api/v1/bracelets")
public class BraceletRestController {

    private final BraceletService braceletService;

    public BraceletRestController(BraceletService braceletService) {
        this.braceletService = braceletService;
    }

    @ApiOperation(value = "Endpoint for adding new bracelet", notes = "Access level ADMIN")
    @PostMapping
    public ResponseEntity<BraceletResponseDto> createBracelet(
            @Valid @RequestBody BraceletRequestDto dto) {
        return new ResponseEntity<>(braceletService.createBracelet(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint for getting all bracelets", notes = "Access level ADMIN")
    @GetMapping
    public List<BraceletResponseDto> getAllBracelets() {
        return braceletService.getAllBracelets();
    }

    @ApiOperation(value = "Endpoint for getting bracelet by id", notes = "Access level ANY")
    @GetMapping("/{id}")
    public ResponseEntity<BraceletResponseDto> getBraceletById(
            @PathVariable(name = "id") String id) {
        return ResponseEntity.ok(braceletService.getBraceletById(id));
    }

    @ApiOperation(value = "Endpoint for updating a bracelet", notes = "Access level ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<BraceletResponseDto> updateBracelet(
            @Valid @RequestBody BraceletRequestDto dto,
            @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(braceletService.updateBracelet(dto, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint for soft deleting bracelet", notes = "Access level ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBracelet(@PathVariable(name = "id") String id) {
        braceletService.deleteBracelet(id);
        return new ResponseEntity<>("Bracelet was deleted successfully!", HttpStatus.OK);
    }
}
