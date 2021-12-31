package com.springboot.klos.service.impl;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.service.ParticipantService;
import com.springboot.klos.service.mappers.ParticipantMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantDao participantDao;
    private final ParticipantMapper mapper;

    public ParticipantServiceImpl(ParticipantDao participantDao, ParticipantMapper mapper) {
        this.participantDao = participantDao;
        this.mapper = mapper;
    }

    @Override
    public ParticipantResponseDto createParticipant(ParticipantRequestDto dto) {
        return mapper.mapToDto(participantDao.save(mapper.mapToModel(dto)));
    }

    @Override
    public List<ParticipantResponseDto> getAllParticipants() {
        return participantDao.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ParticipantResponseDto getParticipantById(Long id) {
        return mapper.mapToDto(participantDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Participant", "id", id.toString())));
    }
}
