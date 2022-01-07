package com.springboot.klos.service;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;

import java.util.List;

public interface BraceletService {
    BraceletResponseDto createBracelet(BraceletRequestDto dto);

    List<BraceletResponseDto> getAllBracelets();

    BraceletResponseDto getBraceletById(String id);

    BraceletResponseDto updateBracelet(BraceletRequestDto dto, String id);

    void deleteBracelet(String id);
}
