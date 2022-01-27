package com.springboot.klos.service.impl;

import com.springboot.klos.model.Lap;
import com.springboot.klos.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ResultDataCalculatorTest {
    private static final String SCANNER_ID = "123456";
    private static final LocalTime SLOWEST_TIME = LocalTime.of(0, 40);
    private static final LocalTime FASTEST_TIME = LocalTime.of(0, 20);
    private static final LocalDateTime FIRST_LAP_DATE_TIME = LocalDateTime.of(2022,11,11, 18, 40);
    private static final LocalDateTime SECOND_LAP_DATE_TIME = LocalDateTime.of(2022,11,11, 18, 40);

    @InjectMocks
    ResultDataCalculator dataCalculator;

    @Test
    void test_calculateStatisticFields() {
        Result entryResult = new Result();
        entryResult.setId(1L);
        entryResult.setBib(100);
        entryResult.setStatus(Result.Status.DNS);

        Lap firstLap = new Lap();
        firstLap.setLapNumber(1);
        firstLap.setScannerId(SCANNER_ID);
        firstLap.setLapTime(SLOWEST_TIME);
        firstLap.setActualTime(FIRST_LAP_DATE_TIME);
        firstLap.setResult(entryResult);

        Lap secondLap = new Lap();
        secondLap.setLapNumber(2);
        secondLap.setScannerId(SCANNER_ID);
        secondLap.setLapTime(FASTEST_TIME);
        secondLap.setActualTime(SECOND_LAP_DATE_TIME);
        secondLap.setResult(entryResult);

        List<Lap> laps = List.of(firstLap, secondLap);

        Result calculatedResult = new Result();
        calculatedResult.setId(1L);
        calculatedResult.setStatus(Result.Status.RUNNING);
        calculatedResult.setBib(100);
        calculatedResult.setFastestLoop(FASTEST_TIME);
        calculatedResult.setSlowestLoop(SLOWEST_TIME);
        calculatedResult.setAverageLoop(LocalTime.of(0, 30));
        calculatedResult.setTotalTimeRunning(LocalTime.of(1, 0));
        calculatedResult.setTotalDistance(13.4);
        calculatedResult.setLapsCompleted(2);

        Result resultFromMethod = dataCalculator.calculateStatisticFields(entryResult, laps);

        assertEquals(calculatedResult, resultFromMethod);
    }
}