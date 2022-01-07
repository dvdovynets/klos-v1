package com.springboot.klos.service.impl;

import com.springboot.klos.dao.EventDao;
import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.dao.ResultDao;
import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.exception.ResourceNotFoundException;
import com.springboot.klos.model.Event;
import com.springboot.klos.model.Participant;
import com.springboot.klos.model.Result;
import com.springboot.klos.model.Lap;
import com.springboot.klos.service.ResultService;
import com.springboot.klos.service.mappers.ResultMapper;
import com.springboot.klos.utils.ResultDataCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ResultServiceImpl implements ResultService {
    private static final Long ZERO = 0L;
    private final ResultDao resultDao;
    private final EventDao eventDao;
    private final ParticipantDao participantDao;
    private final ResultDataCalculator fieldsCalculator;
    private final ResultMapper resultMapper;

    public ResultServiceImpl(ResultDao resultDao,
                             EventDao eventDao,
                             ParticipantDao participantDao,
                             ResultDataCalculator fieldsCalculator,
                             ResultMapper resultMapper) {
        this.resultDao = resultDao;
        this.eventDao = eventDao;
        this.participantDao = participantDao;
        this.fieldsCalculator = fieldsCalculator;
        this.resultMapper = resultMapper;
    }

    @Override
    public ResultResponseDto createResult(ResultRequestDto dto) {

        Long eventId = dto.getEventId();
        Long participantId = dto.getParticipantId();
        Event event = eventDao.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event", "id", eventId.toString()));
        Participant participant = participantDao.findByIdAndIsDeleted(participantId, false).orElseThrow(
                () -> new ResourceNotFoundException("Participant", "id", participantId.toString()));
        Result result = resultMapper.mapToModel(dto);
        result.setEvent(event);
        result.setParticipant(participant);
        return resultMapper.mapToDto(resultDao.save(result));
    }

    @Override
    public List<ResultResponseDto> getAllResults(Long eventId) {
        if (ZERO.equals(eventId)) {
            return resultDao.findAll().stream()
                    .filter(result -> !result.isDeleted())
                    .map(resultMapper::mapToDto)
                    .collect(Collectors.toList());
        }

        Event event = eventDao.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event", "id", eventId.toString()));
        return resultDao.findByEventAndIsDeleted(event, false)
                .stream()
                .map(resultMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResultResponseDto getResultById(Long id) {
        Result result = resultDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Result", "id", id.toString()));
        return resultMapper.mapToDto(result);
    }

    @Override
    public ResultResponseDto updateResult(ResultRequestDto dto, Long id) {
        Result result = resultDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Result", "id", id.toString()));
        result = resultMapper.mapDataToResult(result, dto);
        Result updatedResult = resultDao.save(result);
        return resultMapper.mapToDto(updatedResult);
    }

    @Override
    public void deleteResult(Long id) {
        Result result = resultDao.findByIdAndIsDeleted(id, false).orElseThrow(
                () -> new ResourceNotFoundException("Result", "id", id.toString()));
        result.setDeleted(true);
        resultDao.save(result);
    }

    @Override
    public void updateResultStatistics(Result result, List<Lap> lapsList) {
        Result resultWithUpdatedFields =
                fieldsCalculator.calculateStatisticFields(result, lapsList);
        resultDao.save(resultWithUpdatedFields);
    }
}
