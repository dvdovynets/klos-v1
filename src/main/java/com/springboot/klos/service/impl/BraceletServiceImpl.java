package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.ResultDao;
import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Result;
import com.springboot.klos.service.BraceletService;
import com.springboot.klos.service.mappers.BraceletMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BraceletServiceImpl implements BraceletService {
    private final BraceletDao braceletDao;
    private final ResultDao resultDao;
    private final BraceletMapper mapper;

    public BraceletServiceImpl(BraceletDao braceletDao,
                               ResultDao resultDao,
                               BraceletMapper mapper) {
        this.braceletDao = braceletDao;
        this.resultDao = resultDao;
        this.mapper = mapper;
    }

    @Override
    public BraceletResponseDto createBracelet(BraceletRequestDto dto) {
        Long resultId = dto.getResultId();
        Bracelet bracelet = mapper.mapToModel(dto);

        if (resultId != null) {
            Result result = resultDao.findByIdAndIsDeleted(resultId, false)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Result", "id", resultId.toString()));
            bracelet.setResult(result);
        }
        return mapper.mapToDto(braceletDao.save(bracelet));
    }

    @Override
    public List<BraceletResponseDto> getAllBracelets() {
        return braceletDao.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BraceletResponseDto getBraceletById(String id) {
        return mapper.mapToDto(braceletDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bracelet", "braceletId", id)));
    }

    @Override
    public BraceletResponseDto updateBracelet(BraceletRequestDto dto, String braceletId) {
        Long resultId = dto.getResultId();
        Result result = resultDao.findByIdAndIsDeleted(resultId, false).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Result", "Id", resultId.toString()));
        Bracelet bracelet = braceletDao.findById(braceletId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bracelet", "braceletId", braceletId));

        bracelet.setResult(result);
        return mapper.mapToDto(braceletDao.save(bracelet));
    }

    @Override
    public void deleteBracelet(String id) {
        Bracelet bracelet = braceletDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bracelet", "braceletId", id));
        braceletDao.delete(bracelet);
    }
}
