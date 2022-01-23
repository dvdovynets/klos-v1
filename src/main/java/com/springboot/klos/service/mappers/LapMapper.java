package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.model.Lap;
import com.springboot.klos.utils.DateTimePatternUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class LapMapper implements GenericMapper<Lap, LapRequestDto, LapResponseDto> {
    private static final DateTimeFormatter dateTimeformatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.DATE_TIME_PATTERN);
    private static final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.TIME_PATTERN);

    @Override
    public LapResponseDto mapToDto(Lap lap) {
        LapResponseDto dto = new LapResponseDto();
        dto.setId(lap.getId());
        dto.setLapNumber(lap.getLapNumber());
        dto.setLapTime(lap.getLapTime().format(timeFormatter));
        dto.setActualTime(lap.getActualTime().format(dateTimeformatter));
        dto.setScannerId(lap.getScannerId());
        dto.setResultId(lap.getResult().getId());
        dto.setBib(lap.getResult().getBib());
        return dto;
    }

    @Override
    public Lap mapToModel(LapRequestDto dto) {
        Lap lap = new Lap();
        lap.setLapNumber(dto.getLapNumber());
        lap.setLapTime(LocalTime.parse(dto.getLapTime(), timeFormatter));
        lap.setActualTime(LocalDateTime.parse(dto.getActualTime(), dateTimeformatter));
        lap.setScannerId(dto.getScannerId());
        return lap;
    }

    public Lap mapDataToLap(LapRequestDto dto, Lap lap) {
        lap.setLapNumber(dto.getLapNumber());
        lap.setLapTime(LocalTime.parse(dto.getLapTime(), timeFormatter));
        lap.setActualTime(LocalDateTime.parse(dto.getActualTime(), dateTimeformatter));
        return lap;
    }
}
