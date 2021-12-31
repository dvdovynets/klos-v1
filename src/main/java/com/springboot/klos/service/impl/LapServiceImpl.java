package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.LapDao;
import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Lap;
import com.springboot.klos.model.Participant;
import com.springboot.klos.service.LapService;
import com.springboot.klos.service.mappers.LapMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LapServiceImpl implements LapService {
    private final LapDao lapDao;
    private final BraceletDao braceletDao;
    private final LapMapper mapper;

    public LapServiceImpl(LapDao lapDao, BraceletDao braceletDao, LapMapper mapper) {
        this.lapDao = lapDao;
        this.braceletDao = braceletDao;
        this.mapper = mapper;
    }

    @Override
    public LapResponseDto createLap(LapRequestDto dto) {
        Bracelet bracelet = braceletDao.getById(dto.getBraceletNumber());
        Participant participant = bracelet.getParticipant();
        Lap lap = mapper.mapToModel(dto);
        lap.setParticipant(participant);
        return mapper.mapToDto(lapDao.save(lap));
    }

    @Override
    public List<LapResponseDto> getAllLaps() {
        return lapDao.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public LapResponseDto getLapById(Long id) {
        return mapper.mapToDto(lapDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lap", "id", id.toString())));
    }
}
