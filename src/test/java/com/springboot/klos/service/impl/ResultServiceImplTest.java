package com.springboot.klos.service.impl;

import com.springboot.klos.dao.EventDao;
import com.springboot.klos.dao.LapDao;
import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.dao.ResultDao;
import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.model.Event;
import com.springboot.klos.model.Lap;
import com.springboot.klos.model.Participant;
import com.springboot.klos.model.Result;
import com.springboot.klos.service.mappers.LapMapper;
import com.springboot.klos.service.mappers.ResultMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {
    private static final String DNS = "DNS";
    private static final Participant.Gender GENDER = Participant.Gender.MALE;
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String EMAIL = "email@gmail.com";

    private ResultRequestDto requestDto;
    private ResultResponseDto responseDto;
    private Participant participant;
    private Result result;
    private Event event;
    private LapResponseDto lapResponseDto;
    private List<Lap> laps;

    @InjectMocks
    private ResultServiceImpl resultService;
    @Mock
    private ResultDao resultDao;
    @Mock
    private EventDao eventDao;
    @Mock
    private ParticipantDao participantDao;
    @Mock
    private LapDao lapDao;
    @Mock
    private ResultDataCalculator dataCalculator;
    @Mock
    private ResultMapper resultMapper;
    @Mock
    private LapMapper lapMapper;

    @BeforeEach
    void setUp() {
        requestDto = new ResultRequestDto();
        requestDto.setBib(100);
        requestDto.setEventId(1L);
        requestDto.setParticipantId(1L);
        requestDto.setStatus(DNS);

        participant = new Participant();
        participant.setId(1L);
        participant.setEmail(EMAIL);
        participant.setName(NAME);
        participant.setSurname(SURNAME);

        responseDto = new ResultResponseDto();
        responseDto.setBib(100);
        responseDto.setResultId(1L);
        responseDto.setEventId(1L);
        responseDto.setGender(GENDER.name());
        responseDto.setParticipantFullName(NAME + SURNAME);
        responseDto.setStatus(DNS);

        event = new Event();
        event.setId(1L);
        event.setEventName("KLOS-22");
        event.setEventDate(LocalDate.of(2022, 11, 11));

        result = new Result();
        result.setBib(100);
        result.setStatus(Result.Status.DNS);
        result.setEvent(event);
        result.setParticipant(participant);

        lapResponseDto = new LapResponseDto();
        lapResponseDto.setBib(111);
        lapResponseDto.setLapNumber(1);
        lapResponseDto.setResultId(1L);
        lapResponseDto.setScannerId("123456");

        Lap lap = new Lap();
        lap.setLapNumber(1);
        lap.setResult(result);
        lap.setScannerId("123456");

        laps = List.of(lap);
    }

    @Test
    void createResult() {
        when(eventDao.findById(1L)).thenReturn(Optional.of(event));
        when(participantDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(participant));
        when(resultMapper.mapToModel(requestDto)).thenReturn(result);
        when(resultDao.save(result)).thenReturn(result);
        when(resultMapper.mapToDto(result)).thenReturn(responseDto);

        ResultResponseDto resultFromDb = resultService.createResult(requestDto);

        assertEquals(responseDto, resultFromDb);

        verify(eventDao).findById(1L);
        verify(participantDao).findByIdAndIsDeleted(1L, false);
        verify(resultDao).save(result);
    }

    @Test
    void getAllResults() {
        when(resultDao.findAll()).thenReturn(List.of(result, result));
        when(resultDao.findByEventAndIsDeleted(event, false)).thenReturn(List.of(result));
        when(resultMapper.mapToDto(any())).thenReturn(responseDto);
        when(eventDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(event));

        List<ResultResponseDto> allResults = resultService.getAllResults(0L);
        assertEquals(allResults.size(), 2);

        List<ResultResponseDto> firstEventResults = resultService.getAllResults(1L);
        assertEquals(firstEventResults.size(), 1);

        verify(resultDao).findAll();
        verify(resultDao).findByEventAndIsDeleted(event, false);
    }

    @Test
    void getAllLapsForResult() {
        when(resultDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(result));
        when(lapDao.findByResult(result)).thenReturn(laps);
        when(lapMapper.mapToDto(any())).thenReturn(lapResponseDto);

        List<LapResponseDto> allLapsForResult = resultService.getAllLapsForResult(1L);
        assertEquals(laps.size(), allLapsForResult.size());

        verify(resultDao).findByIdAndIsDeleted(1L, false);
        verify(lapDao).findByResult(result);
    }

    @Test
    void getResultById() {
        when(resultDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(result));
        when(resultMapper.mapToDto(result)).thenReturn(responseDto);

        ResultResponseDto resultFromDb = resultService.getResultById(1L);

        assertEquals(responseDto, resultFromDb);

        verify(resultDao).findByIdAndIsDeleted(1L, false);
    }

    @Test
    void updateResult() {
        when(resultDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(result));
        when(resultDao.save(result)).thenReturn(result);
        when(resultMapper.mapDataToResult(result, requestDto)).thenReturn(result);
        when(resultMapper.mapToDto(result)).thenReturn(responseDto);

        ResultResponseDto resultFromDb = resultService.updateResult(requestDto, 1L);

        assertEquals(responseDto, resultFromDb);

        verify(resultDao).findByIdAndIsDeleted(1L, false);
        verify(resultDao).save(result);
    }

    @Test
    void deleteResult() {
        when(resultDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(result));
        when(resultDao.save(result)).thenReturn(result);

        resultService.deleteResult(1L);

        verify(resultDao).findByIdAndIsDeleted(1L, false);
        verify(resultDao).save(result);
    }

    @Test
    void updateResultStatistics() {
        when(dataCalculator.calculateStatisticFields(result, laps)).thenReturn(result);
        when(resultDao.save(result)).thenReturn(result);

        resultService.updateResultStatistics(result, laps);

        verify(dataCalculator).calculateStatisticFields(result, laps);
        verify(resultDao).save(result);
    }
}