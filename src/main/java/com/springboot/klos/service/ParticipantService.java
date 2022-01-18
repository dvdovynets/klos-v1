package com.springboot.klos.service;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;

import java.util.List;

public interface ParticipantService {
    ParticipantResponseDto createParticipant(ParticipantRequestDto dto);

    List<ParticipantResponseDto> getAllParticipants();

    ParticipantResponseDto getParticipantById(Long id);

    ParticipantResponseDto updateParticipant(ParticipantRequestDto dto, Long id);

    void deleteParticipant(Long id);

    boolean checkIfEmailExists(String email);

    ParticipantResponseDto createAdmin(ParticipantRequestDto dto);

    void createDefaultAdminAndRoles();
}
