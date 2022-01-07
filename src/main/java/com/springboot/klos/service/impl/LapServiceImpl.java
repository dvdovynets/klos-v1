package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.LapDao;
import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Result;
import com.springboot.klos.model.Lap;
import com.springboot.klos.service.ResultService;
import com.springboot.klos.service.LapService;
import com.springboot.klos.service.mappers.LapMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LapServiceImpl implements LapService {
    private final ResultService resultService;
    private final LapDao lapDao;
    private final BraceletDao braceletDao;
    private final LapMapper mapper;

    public LapServiceImpl(ResultService resultService,
                          LapDao lapDao,
                          BraceletDao braceletDao,
                          LapMapper mapper) {
        this.resultService = resultService;
        this.lapDao = lapDao;
        this.braceletDao = braceletDao;
        this.mapper = mapper;
    }

    @Override
    public LapResponseDto createLap(LapRequestDto dto) {
        String braceletId = dto.getBraceletId();
        Bracelet bracelet = braceletDao.findById(braceletId).orElseThrow(
                () -> new ResourceNotFoundException("Bracelet", "id", braceletId));
        Result result = bracelet.getResult();

        Lap lap = mapper.mapToModel(dto);
        lap.setResult(result);
        lap = lapDao.save(lap);

        List<Lap> lapsList = lapDao.findByResult(result);
        resultService.updateResultStatistics(result, lapsList);

        return mapper.mapToDto(lap);
    }

    @Override
    public List<LapResponseDto> getAllLaps() {
        return lapDao.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LapResponseDto getLapById(Long id) {
        return mapper.mapToDto(lapDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lap", "id", id.toString())));
    }

    @Override
    public LapResponseDto updateLap(LapRequestDto dto, Long id) {
        Lap lap = lapDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lap", "id", id.toString()));
        lap = mapper.mapDataToLap(dto, lap);
        return mapper.mapToDto(lapDao.save(lap));
    }

    @Override
    public void deleteLap(Long id) {
        Lap lap = lapDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lap", "id", id.toString()));
        lapDao.delete(lap);
    }
}
