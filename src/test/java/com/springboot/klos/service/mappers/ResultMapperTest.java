package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.model.Event;
import com.springboot.klos.model.Participant;
import com.springboot.klos.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultMapperTest {
    @InjectMocks
    private ResultMapper mapper;
    private ResultRequestDto requestDto;
    private ResultResponseDto responseDto;
    private Result result;

    @BeforeEach
    void setUp() {
        requestDto = new ResultRequestDto();
        requestDto.setStatus("DNS");
        requestDto.setBib(111);
        requestDto.setParticipantId(1L);
        requestDto.setEventId(1L);

        responseDto = new ResultResponseDto();
        responseDto.setStatus("DNS");
        responseDto.setBib(111);
        responseDto.setGender("MALE");
        responseDto.setResultId(1L);
        responseDto.setEventId(1L);
        responseDto.setParticipantFullName("surname name");
        responseDto.setAverageLoop("00:30:00");
        responseDto.setFastestLoop("00:20:00");
        responseDto.setSlowestLoop("00:40:00");
        responseDto.setTotalDistance(13.4);
        responseDto.setTotalTimeRunning("01:00:00");
        responseDto.setLapsCompleted(2);
        responseDto.setParticipantId(1L);

        Event event = new Event();
        event.setId(1L);
        event.setEventName("KLOS-22");
        event.setEventDate(LocalDate.of(2022, 11, 11));

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("name");
        participant.setSurname("surname");
        participant.setGender(Participant.Gender.MALE);

        result = new Result();
        result.setStatus(Result.Status.DNS);
        result.setBib(111);
        result.setAverageLoop(LocalTime.of(0, 30));
        result.setFastestLoop(LocalTime.of(0, 20));
        result.setSlowestLoop(LocalTime.of(0, 40));
        result.setTotalDistance(13.4);
        result.setTotalTimeRunning(LocalTime.of(1, 0));
        result.setLapsCompleted(2);
        result.setEvent(event);
        result.setParticipant(participant);
    }

    @Test
    void mapToModel() {
        Result expected = new Result();
        expected.setStatus(Result.Status.DNS);
        expected.setBib(111);

        Result actual = mapper.mapToModel(requestDto);
        assertEquals(expected, actual);
    }

    @Test
    void mapToDto() {
        result.setId(1L);

        ResultResponseDto actual = mapper.mapToDto(result);
        assertEquals(responseDto, actual);
    }

    @Test
    void mapDataToResult() {
        Result expected = new Result();
        expected.setStatus(Result.Status.DNS);
        expected.setBib(111);

        Result actual = mapper.mapDataToResult(new Result(), requestDto);
        assertEquals(expected, actual);
    }
}