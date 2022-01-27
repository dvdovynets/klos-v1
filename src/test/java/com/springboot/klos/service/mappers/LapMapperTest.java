package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.model.Lap;
import com.springboot.klos.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LapMapperTest {
    @InjectMocks
    private LapMapper lapMapper;
    private LapResponseDto responseDto;
    private LapRequestDto requestDto;
    private Lap lap;
    private Result result;

    @BeforeEach
    void setUp() {
        requestDto = new LapRequestDto();
        requestDto.setBraceletId("AA:BB:CC");
        requestDto.setScannerId("12345");
        requestDto.setLapTime("00:40:30");
        requestDto.setLapNumber(2);
        requestDto.setActualTime("11.11.2022 18:40");

        responseDto = new LapResponseDto();
        responseDto.setId(1L);
        responseDto.setResultId(1L);
        responseDto.setBib(111);
        responseDto.setLapNumber(2);
        responseDto.setScannerId("12345");
        responseDto.setLapTime("00:40:30");
        responseDto.setActualTime("11.11.2022 18:40");

        result = new Result();
        result.setId(1L);
        result.setBib(111);

        lap = new Lap();
        lap.setLapTime(LocalTime.of(0, 40, 30));
        lap.setActualTime(LocalDateTime.of(2022, 11, 11, 18 ,40));
        lap.setScannerId("12345");
        lap.setLapNumber(2);
    }

    @Test
    void mapToDto() {
        lap.setId(1L);
        lap.setResult(result);
        LapResponseDto mappedResponse = lapMapper.mapToDto(lap);
        assertEquals(responseDto, mappedResponse);
    }

    @Test
    void mapToModel() {
        Lap mappedLap = lapMapper.mapToModel(requestDto);
        assertEquals(lap, mappedLap);
    }

    @Test
    void mapDataToLap() {
        Lap mappedLap = lapMapper.mapDataToLap(requestDto, new Lap());
        Lap expectedLap = new Lap();
        expectedLap.setLapNumber(2);
        expectedLap.setLapTime(LocalTime.of(0, 40, 30));
        expectedLap.setActualTime(LocalDateTime.of(2022, 11, 11, 18 ,40));
        assertEquals(expectedLap, mappedLap);
    }
}