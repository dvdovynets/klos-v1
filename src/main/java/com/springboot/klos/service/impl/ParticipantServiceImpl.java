package com.springboot.klos.service.impl;

import com.springboot.klos.dao.RoleDao;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Participant;
import com.springboot.klos.model.Role;
import com.springboot.klos.service.ParticipantService;
import com.springboot.klos.service.mappers.ParticipantMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantDao participantDao;
    private final RoleDao roleDao;
    private final ParticipantMapper participantMapper;

    public ParticipantServiceImpl(ParticipantDao participantDao,
                                  RoleDao roleDao,
                                  ParticipantMapper participantMapper) {
        this.participantDao = participantDao;
        this.roleDao = roleDao;
        this.participantMapper = participantMapper;
    }

    @Override
    public ParticipantResponseDto createParticipant(ParticipantRequestDto dto) {
        Role roleUser = roleDao.findByName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));
        Participant participant = participantMapper.mapToModel(dto);
        participant.setRoles(Collections.singleton(roleUser));
        return participantMapper.mapToDto(participantDao.save(participant));
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

    @Override
    public boolean checkIfEmailExists(String email) {
        return participantDao.existsByEmail(email);
    }

    @Override
    public ParticipantResponseDto createAdmin(ParticipantRequestDto dto) {
        Role roleAdmin = roleDao.findByName(Role.RoleName.ROLE_ADMIN).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", "ROLE_ADMIN"));
        Role roleUser = roleDao.findByName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));
        Participant participant = participantMapper.mapToModel(dto);
        participant.setRoles(Set.of(roleUser, roleAdmin));
        return participantMapper.mapToDto(participantDao.save(participant));
    }
}
