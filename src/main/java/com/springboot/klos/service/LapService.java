package com.springboot.klos.service;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.model.Lap;

import java.util.List;

public interface LapService {
    LapResponseDto createLap(LapRequestDto dto);

    List<LapResponseDto> getAllLaps();

    LapResponseDto getLapById(Long id);
}
