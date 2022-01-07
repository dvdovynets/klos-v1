package com.springboot.klos.service.impl;

import com.springboot.klos.dao.LapDao;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Participant;
import com.springboot.klos.service.ParticipantService;
import com.springboot.klos.service.mappers.LapMapper;
import com.springboot.klos.service.mappers.ParticipantMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantDao participantDao;
    private final ParticipantMapper participantMapper;

    public ParticipantServiceImpl(ParticipantDao participantDao,
                                  ParticipantMapper participantMapper) {
        this.participantDao = participantDao;
        this.participantMapper = participantMapper;
    }

    @Override
    public ParticipantResponseDto createParticipant(ParticipantRequestDto dto) {
        return participantMapper.mapToDto(participantDao.save(participantMapper.mapToModel(dto)));
    }

    @Override
    public List<ParticipantResponseDto> getAllParticipants() {
        return participantDao.findAll().stream()
                .filter(p -> !p.isDeleted())
                .map(participantMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantResponseDto getParticipantById(Long id) {
        Participant participant = participantDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Participant", "id", id.toString()));
        return participantMapper.mapToDto(participant);
    }

    @Override
    public ParticipantResponseDto updateParticipant(ParticipantRequestDto dto, Long id) {
        Participant participant = participantDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Participant", "id", id.toString()));
        participant = participantMapper.mapDataToParticipant(dto, participant);
        return participantMapper.mapToDto(participantDao.save(participant));
    }

    @Override
    public void deleteParticipant(Long id) {
        Participant participant = participantDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Participant", "id", id.toString()));
        participant.setDeleted(true);
        participantDao.save(participant);
    }
}
