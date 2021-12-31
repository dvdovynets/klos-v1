package com.springboot.klos.service;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.model.Participant;

import java.util.List;

public interface ParticipantService {
    ParticipantResponseDto createParticipant(ParticipantRequestDto dto);

    List<ParticipantResponseDto> getAllParticipants();

    ParticipantResponseDto getParticipantById(Long id);
}
