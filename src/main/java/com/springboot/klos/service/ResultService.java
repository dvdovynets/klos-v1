package com.springboot.klos.service;

import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.model.Result;
import com.springboot.klos.model.Lap;

import java.util.List;

public interface ResultService {
    ResultResponseDto createResult(ResultRequestDto dto);

    List<ResultResponseDto> getAllResults(Long eventId);

    List<LapResponseDto> getAllLapsForResult(Long id);

    ResultResponseDto getResultById(Long id);

    ResultResponseDto updateResult(ResultRequestDto dto, Long id);

    void deleteResult(Long id);

    void updateResultStatistics(Result result, List<Lap> lapsList);
}
