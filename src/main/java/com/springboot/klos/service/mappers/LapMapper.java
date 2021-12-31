package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.model.Lap;
import com.springboot.klos.utils.DateTimePatternUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LapMapper {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.DATE_TIME_PATTERN);

    public Lap mapToModel(LapRequestDto dto) {
        Lap lap = new Lap();
        lap.setLapNumber(dto.getLapNumber());
        lap.setLapTime(LocalDateTime.parse(dto.getLapTime(), formatter));
        lap.setActualTime(LocalDateTime.parse(dto.getActualTime(), formatter));
        return lap;
    }

    public LapResponseDto mapToDto(Lap lap) {
        LapResponseDto dto = new LapResponseDto();
        dto.setId(lap.getId());
        dto.setLapNumber(lap.getLapNumber());
        dto.setLapTime(lap.getLapTime().format(formatter));
        dto.setActualTime(lap.getActualTime().format(formatter));
        dto.setScannerId(lap.getScannerId());
        dto.setParticipantId(lap.getParticipant().getId());
        return dto;
    }
}
