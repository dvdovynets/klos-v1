package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.model.Result;
import com.springboot.klos.model.Participant;
import com.springboot.klos.utils.DateTimePatternUtil;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ResultMapper implements GenericMapper<Result, ResultRequestDto, ResultResponseDto> {
    private static final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.TIME_PATTERN);

    @Override
    public Result mapToModel(ResultRequestDto dto) {
        Result result = new Result();
        result.setStatus(Result.Status.valueOf(dto.getStatus()));
        return result;
    }

    @Override
    public ResultResponseDto mapToDto(Result result) {
        ResultResponseDto dto = new ResultResponseDto();
        dto.setResultId(result.getId());
        dto.setStatus(result.getStatus().name());
        dto.setLapsCompleted(result.getLapsCompleted());
        dto.setTotalDistance(result.getTotalDistance());
        dto.setFastestLoop(result.getFastestLoop().format(timeFormatter));
        dto.setSlowestLoop(result.getSlowestLoop().format(timeFormatter));
        dto.setAverageLoop(result.getAverageLoop().format(timeFormatter));
        dto.setTotalTimeRunning(result.getTotalTimeRunning().format(timeFormatter));
        dto.setEventId(result.getEvent().getId());
        return fillParticipantData(result.getParticipant(), dto);
    }

    public Result mapDataToResult(Result result, ResultRequestDto dto) {
        result.setStatus(Result.Status.valueOf(dto.getStatus()));
        return result;
    }

    private ResultResponseDto fillParticipantData(Participant participant, ResultResponseDto dto) {
        dto.setParticipantId(participant.getId());
        dto.setParticipantFullName(createParticipantFullName(participant));
        dto.setGender(participant.getGender().name());
        return dto;
    }

    private String createParticipantFullName(Participant participant) {
        return participant.getSurname() + " " + participant.getName();
    }
}
