package com.springboot.klos.service.impl;

import com.springboot.klos.dao.BraceletDao;
import com.springboot.klos.dao.ResultDao;
import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Result;
import com.springboot.klos.service.BraceletService;
import com.springboot.klos.service.mappers.BraceletMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BraceletServiceImplTest {
    @InjectMocks
    BraceletService braceletService;
    @InjectMocks
    BraceletDao braceletDao;
    @InjectMocks
    ResultDao resultDao;
    @InjectMocks
    BraceletMapper braceletMapper;


    @Test
    void createBracelet() {
//        BraceletRequestDto requestDto = new BraceletRequestDto();
//        requestDto.setBraceletId("AA:BB:CC:DD:FF");
//        requestDto.setResultId(1L);
//
//        Result result = new Result();
//        result.setId(1L);
//
//        Bracelet bracelet = new Bracelet();
//        bracelet.setBraceletId("AA:BB:CC:DD:FF");
//        bracelet.setResult(result);
//
//        when(braceletMapper.mapToModel(requestDto)).thenReturn(bracelet);
//        when(resultDao.findByIdAndIsDeleted(1L, false).orElseThrow()).thenReturn(result);
//        when(braceletDao.save(bracelet)).thenReturn(bracelet);
//        BraceletResponseDto responseDto = braceletService.createBracelet(requestDto);
//        assertEquals(requestDto.getBraceletId(), responseDto.getBraceletId());
//        assertEquals(requestDto.getResultId(), responseDto.getResultId());
    }

    @Test
    void getAllBracelets() {
    }

    @Test
    void getBraceletById() {
    }

    @Test
    void updateBracelet() {
    }

    @Test
    void deleteBracelet() {
    }
}