package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BraceletMapperTest {
    @InjectMocks
    private BraceletMapper mapper;

    private Bracelet braceletWithId;
    private Bracelet braceletWithoutId;
    private BraceletRequestDto requestDto;
    private BraceletResponseDto responseDto;

    @BeforeEach
    void setUp() {
        Result result = new Result();
        result.setId(1L);
        result.setStatus(Result.Status.DNS);
        result.setBib(111);

        braceletWithId = new Bracelet();
        braceletWithId.setBraceletId("AA:BB:CC");
        braceletWithId.setResult(result);

        braceletWithoutId = new Bracelet();
        braceletWithoutId.setBraceletId("AA:BB:CC");

        requestDto = new BraceletRequestDto();
        requestDto.setResultId(1L);
        requestDto.setBraceletId("AA:BB:CC");

        responseDto = new BraceletResponseDto();
        responseDto.setResultId(1L);
        responseDto.setBraceletId("AA:BB:CC");
    }

    @Test
    void mapToModel() {
        Bracelet braceletFromDb = mapper.mapToModel(requestDto);
        assertEquals(braceletWithoutId, braceletFromDb);
    }

    @Test
    void mapToDto() {
        BraceletResponseDto responseFromDb = mapper.mapToDto(braceletWithId);
        assertEquals(responseDto, responseFromDb);
    }
}