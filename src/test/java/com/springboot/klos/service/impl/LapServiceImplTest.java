package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.LapDao;
import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Lap;
import com.springboot.klos.model.Result;
import com.springboot.klos.service.ResultService;
import com.springboot.klos.service.mappers.LapMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LapServiceImplTest {
    private static final int LAP_NUMBER = 1;
    private static final String LAP_TIME = "00:40:30";
    private static final String ACTUAL_TIME = "21.11.2022 18:40";
    private static final LocalTime LAP_LOCAL_TIME = LocalTime.of(0, 40, 30);
    private static final LocalDateTime ACTUAL_LOCAL_TIME = LocalDateTime.of(2022, 11, 11, 18, 40);
    private static final String SCANNER_ID = "123456";
    private static final String BRACELET_ID = "AA:BB:CC:DD:EE";
    private LapRequestDto requestDto;
    private Result result;
    private Lap lap;
    private LapResponseDto responseDto;
    private Bracelet bracelet;


    @InjectMocks
    private LapServiceImpl lapService;
    @Mock
    private ResultService resultService;
    @Mock
    private LapDao lapDao;
    @Mock
    private LapMapper lapMapper;
    @Mock
    private BraceletDao braceletDao;

    @BeforeEach
    void setUp() {
        requestDto = new LapRequestDto();
        requestDto.setLapNumber(LAP_NUMBER);
        requestDto.setLapTime(LAP_TIME);
        requestDto.setActualTime(ACTUAL_TIME);
        requestDto.setScannerId(SCANNER_ID);
        requestDto.setBraceletId(BRACELET_ID);

        result = new Result();
        result.setId(1L);

        lap = new Lap();
        lap.setLapNumber(LAP_NUMBER);
        lap.setLapTime(LAP_LOCAL_TIME);
        lap.setActualTime(ACTUAL_LOCAL_TIME);
        lap.setScannerId(SCANNER_ID);
        lap.setResult(result);

        responseDto = new LapResponseDto();
        responseDto.setId(1L);
        responseDto.setLapTime(LAP_TIME);
        responseDto.setActualTime(ACTUAL_TIME);
        responseDto.setLapNumber(LAP_NUMBER);
        responseDto.setScannerId(SCANNER_ID);
        responseDto.setResultId(1L);

        bracelet = new Bracelet();
        bracelet.setBraceletId("AA:BB:CC:DD:EE");
        bracelet.setResult(result);
    }

    @Test
    void createLap() {
        List<Lap> laps = List.of(lap);
        when(braceletDao.findById(anyString())).thenReturn(Optional.of(bracelet));
        when(lapMapper.mapToModel(requestDto)).thenReturn(lap);
        when(lapDao.save(lap)).thenReturn(lap);
        when(lapDao.findByResult(result)).thenReturn(laps);
        doNothing().when(resultService).updateResultStatistics(result, laps);
        when(lapMapper.mapToDto(lap)).thenReturn(responseDto);

        LapResponseDto lapFromDb = lapService.createLap(requestDto);

        assertEquals(responseDto, lapFromDb);

        verify(resultService).updateResultStatistics(result, laps);
        verify(lapDao).existsByResultAndLapNumber(result, LAP_NUMBER);
        verify(lapDao).save(lap);
        verify(braceletDao).findById(anyString());
    }

    @Test
    void getAllLaps() {
        List<Lap> laps = List.of(this.lap);
        when(lapDao.findAll()).thenReturn(laps);
        when(lapMapper.mapToDto(any())).thenReturn(responseDto);
        List<LapResponseDto> allLapsFromDb = lapService.getAllLaps();

        assertEquals(laps.size(), allLapsFromDb.size());

        verify(lapDao).findAll();
    }

    @Test
    void getLapById() {
        when(lapDao.findById(anyLong())).thenReturn(Optional.of(lap));
        when(lapMapper.mapToDto(lap)).thenReturn(responseDto);
        LapResponseDto lapFromDb = lapService.getLapById(1L);

        assertEquals(responseDto, lapFromDb);

        verify(lapDao).findById(1L);
    }

    @Test
    void updateLap() {
        when(lapDao.findById(1L)).thenReturn(Optional.of(lap));
        when(lapDao.save(lap)).thenReturn(lap);
        when(lapMapper.mapDataToLap(requestDto, lap)).thenReturn(lap);
        when(lapMapper.mapToDto(lap)).thenReturn(responseDto);

        LapResponseDto updatedLap = lapService.updateLap(requestDto, 1L);

        assertEquals(responseDto, updatedLap);

        verify(lapDao).findById(1L);
        verify(lapMapper).mapDataToLap(any(), any());
        verify(lapDao).save(lap);
    }

    @Test
    void deleteLap() {
        when(lapDao.findById(1L)).thenReturn(Optional.of(lap));

        lapService.deleteLap(1L);

        verify(lapDao).findById(1L);
        verify(lapDao).delete(lap);
    }
}