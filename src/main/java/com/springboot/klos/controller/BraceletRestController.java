package com.springboot.klos.controller;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.service.BraceletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bracelets")
public class BraceletRestController {

    private final BraceletService braceletService;

    public BraceletRestController(BraceletService braceletService) {
        this.braceletService = braceletService;
    }

    @PostMapping
    public ResponseEntity<BraceletResponseDto> createBracelet(
            @RequestBody BraceletRequestDto dto) {
        return new ResponseEntity<>(braceletService.createBracelet(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<BraceletResponseDto> getAllBracelets() {
        return braceletService.getAllBracelets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BraceletResponseDto> getBraceletById(
            @PathVariable(name = "id") String id) {
        return ResponseEntity.ok(braceletService.getBraceletById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BraceletResponseDto> updateBracelet(
            @RequestBody BraceletRequestDto dto,
            @PathVariable(name = "id") String id) {
        return new ResponseEntity<>(braceletService.updateBracelet(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBracelet(@PathVariable(name = "id") String id) {
        braceletService.deleteBracelet(id);
        return new ResponseEntity<>("Bracelet was successfully deleted!", HttpStatus.OK);
    }
}
