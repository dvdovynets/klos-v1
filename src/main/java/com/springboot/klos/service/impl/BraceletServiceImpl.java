package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Participant;
import com.springboot.klos.service.BraceletService;
import com.springboot.klos.service.mappers.BraceletMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BraceletServiceImpl implements BraceletService {

    private final BraceletDao braceletDao;
    private final ParticipantDao participantDao;
    private final BraceletMapper mapper;

    public BraceletServiceImpl(BraceletDao braceletDao,
                               ParticipantDao participantDao,
                               BraceletMapper mapper) {
        this.braceletDao = braceletDao;
        this.participantDao = participantDao;
        this.mapper = mapper;
    }

    @Override
    public BraceletResponseDto createBracelet(BraceletRequestDto dto) {
        Long participantId = dto.getParticipantId();
        Bracelet bracelet = mapper.mapToModel(dto);

        if (participantId != null) {
            Participant participant = participantDao.findById(participantId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Participant", "id", participantId.toString()));
            bracelet.setParticipant(participant);
        }
        return mapper.mapToDto(braceletDao.save(bracelet));
    }

    @Override
    public List<BraceletResponseDto> getAllBracelets() {
        return braceletDao.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BraceletResponseDto getBraceletById(String id) {
        return mapper.mapToDto(braceletDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bracelet", "braceletId", id)));
    }

    @Override
    public BraceletResponseDto updateBracelet(BraceletRequestDto dto, String braceletId) {
        Long participantId = dto.getParticipantId();
        Participant participant = participantDao.findById(participantId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Participant", "braceletId", participantId.toString()));
        Bracelet bracelet = braceletDao.findById(braceletId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bracelet", "braceletId", braceletId));

        bracelet.setParticipant(participant);
        return mapper.mapToDto(braceletDao.save(bracelet));
    }
}
