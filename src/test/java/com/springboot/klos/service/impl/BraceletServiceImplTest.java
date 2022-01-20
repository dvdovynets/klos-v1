package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.ResultDao;
import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Result;
import com.springboot.klos.service.mappers.BraceletMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BraceletServiceImplTest {
    @InjectMocks
    private BraceletServiceImpl braceletService;
    @Mock
    private BraceletDao braceletDao;
    @Mock
    private ResultDao resultDao;
    @Mock
    private BraceletMapper braceletMapper;

    @Test
    void test_createBracelet() {
        BraceletRequestDto requestDto = new BraceletRequestDto();
        requestDto.setBraceletId("AA:BB:CC:DD:FF");
        requestDto.setResultId(1L);

        Result result = new Result();
        result.setId(1L);

        Bracelet bracelet = new Bracelet();
        bracelet.setBraceletId("AA:BB:CC:DD:FF");
        bracelet.setResult(result);

        BraceletResponseDto responseDto = new BraceletResponseDto();
        responseDto.setBraceletId("AA:BB:CC:DD:FF");
        responseDto.setResultId(1L);

        when(braceletMapper.mapToModel(requestDto)).thenReturn(bracelet);
        when(braceletMapper.mapToDto(bracelet)).thenReturn(responseDto);
        when(resultDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(result));
        when(braceletDao.save(bracelet)).thenReturn(bracelet);
        BraceletResponseDto braceletFromDb = braceletService.createBracelet(requestDto);
        assertEquals(braceletFromDb, responseDto);

        verify(resultDao).findByIdAndIsDeleted(anyLong(), anyBoolean());
        verify(braceletDao).save(any());
    }

    @Test
    void test_getAllBracelets() {
        Result firstResult = new Result();
        firstResult.setId(1L);
        Result secondResult = new Result();
        secondResult.setId(2L);

        Bracelet firstBracelet = new Bracelet();
        firstBracelet.setBraceletId("AA:BB:CC:DD:FF");
        firstBracelet.setResult(secondResult);
        Bracelet secondBracelet = new Bracelet();
        secondBracelet.setBraceletId("BB:CC:DD:EE:FF");
        secondBracelet.setResult(secondResult);

        BraceletResponseDto firstResponse = new BraceletResponseDto();
        firstResponse.setBraceletId("AA:BB:CC:DD:FF");
        firstResponse.setResultId(1L);
        BraceletResponseDto secondResponse = new BraceletResponseDto();
        secondResponse.setBraceletId("BB:CC:DD:EE:FF");
        secondResponse.setResultId(2L);

        List<Bracelet> bracelets = List.of(firstBracelet, secondBracelet);
        when(braceletDao.findAll()).thenReturn(bracelets);
        when(braceletMapper.mapToDto(firstBracelet)).thenReturn(firstResponse);
        when(braceletMapper.mapToDto(secondBracelet)).thenReturn(secondResponse);

        List<BraceletResponseDto> braceletDtos = braceletService.getAllBracelets();

        assertEquals(bracelets.size(), braceletDtos.size());

        verify(braceletDao).findAll();
        verify(braceletMapper, times(2)).mapToDto(any());
    }

    @Test
    void test_getBraceletById() {
        Result result = new Result();
        result.setId(1L);

        Bracelet bracelet = new Bracelet();
        bracelet.setBraceletId("AA:BB:CC:DD:FF");
        bracelet.setResult(result);

        BraceletResponseDto responseDto = new BraceletResponseDto();
        responseDto.setBraceletId("AA:BB:CC:DD:FF");
        responseDto.setResultId(1L);

        when(braceletMapper.mapToDto(bracelet)).thenReturn(responseDto);
        when(braceletDao.findById("AA:BB:CC:DD:FF")).thenReturn(Optional.of(bracelet));

        BraceletResponseDto braceletFromDb = braceletService.getBraceletById("AA:BB:CC:DD:FF");

        assertEquals(responseDto, braceletFromDb);
    }

    @Test
    void test_updateBracelet() {
        Bracelet bracelet = new Bracelet();
        bracelet.setBraceletId("AA:BB:CC:DD:FF");

        Result result = new Result();
        result.setId(1L);

        Bracelet braceletWithResult = new Bracelet();
        braceletWithResult.setBraceletId("AA:BB:CC:DD:FF");
        braceletWithResult.setResult(result);

        BraceletRequestDto requestDto = new BraceletRequestDto();
        requestDto.setBraceletId("AA:BB:CC:DD:FF");
        requestDto.setResultId(1L);

        BraceletResponseDto responseDto = new BraceletResponseDto();
        responseDto.setBraceletId("AA:BB:CC:DD:FF");
        responseDto.setResultId(1L);

        when(braceletMapper.mapToDto(braceletWithResult)).thenReturn(responseDto);
        when(resultDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(result));
        when(braceletDao.findById("AA:BB:CC:DD:FF")).thenReturn(Optional.of(bracelet));
        when(braceletDao.save(any())).thenReturn(braceletWithResult);

        BraceletResponseDto updatedBraceletDto =
                braceletService.updateBracelet(requestDto, "AA:BB:CC:DD:FF");

        assertEquals(responseDto, updatedBraceletDto);

        verify(resultDao).findByIdAndIsDeleted(anyLong(), anyBoolean());
        verify(braceletDao).save(any());
    }

    @Test
    void test_deleteBracelet() {
        Bracelet bracelet = new Bracelet();
        bracelet.setBraceletId("AA:BB:CC:DD:FF");

        when(braceletDao.findById("AA:BB:CC:DD:FF")).thenReturn(Optional.of(bracelet));
        braceletService.deleteBracelet("AA:BB:CC:DD:FF");

        verify(braceletDao).delete(bracelet);
    }
}