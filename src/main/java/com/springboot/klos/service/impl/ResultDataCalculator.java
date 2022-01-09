package com.springboot.klos.service.impl;

import com.springboot.klos.model.Result;
import com.springboot.klos.model.Lap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResultDataCalculator {
    private static final double LAP_DISTANCE = 6.7;

    public Result calculateStatisticFields(Result result, List<Lap> laps) {
        if (result.getStatus() == Result.Status.DNS) {
            result.setStatus(Result.Status.RUNNING);
        }
        result.setLapsCompleted(laps.size());
        result.setTotalDistance(calculateTotalDistance(result.getLapsCompleted()));
        return calculateLapTimes(result, laps);
    }

    private double calculateTotalDistance(int numberOfLaps) {
        return new BigDecimal(numberOfLaps * LAP_DISTANCE)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Result calculateLapTimes(Result result, List<Lap> laps) {
        List<LocalTime> lapTimeList = laps.stream().map(Lap::getLapTime).collect(Collectors.toList());
        result.setFastestLoop(calculateFastestLoop(lapTimeList));
        result.setSlowestLoop(calculateSlowestLoop(lapTimeList));
        result.setAverageLoop(calculateAvgLapTime(lapTimeList));
        result.setTotalTimeRunning(calculateTotalTimeRunning(lapTimeList));
        return result;
    }

    private LocalTime calculateSlowestLoop(List<LocalTime> lapTimeList) {
        long slowestLoop = lapTimeList.stream()
                .mapToLong(LocalTime::toNanoOfDay)
                .max()
                .orElseThrow();
        return LocalTime.ofNanoOfDay(slowestLoop);
    }

    private LocalTime calculateFastestLoop(List<LocalTime> lapTimeList) {
        long fastestLoop = lapTimeList.stream()
                .mapToLong(LocalTime::toNanoOfDay)
                .min()
                .orElseThrow();
        return LocalTime.ofNanoOfDay(fastestLoop);
    }

    private LocalTime calculateAvgLapTime(List<LocalTime> lapTimeList) {
        double avgLapTime = lapTimeList.stream()
                .mapToLong(LocalTime::toNanoOfDay)
                .average()
                .orElseThrow();
        return LocalTime.ofNanoOfDay(Math.round(avgLapTime));
    }

    private LocalTime calculateTotalTimeRunning(List<LocalTime> lapTimeList) {
        long totalTimeRunning = lapTimeList.stream()
                .mapToLong(LocalTime::toNanoOfDay)
                .sum();
        return LocalTime.ofNanoOfDay(totalTimeRunning);
    }

}
