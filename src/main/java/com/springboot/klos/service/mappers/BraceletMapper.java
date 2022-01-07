package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Result;
import org.springframework.stereotype.Component;

@Component
public class BraceletMapper implements GenericMapper<Bracelet, BraceletRequestDto, BraceletResponseDto> {
    @Override
    public Bracelet mapToModel(BraceletRequestDto dto) {
        Bracelet bracelet = new Bracelet();
        bracelet.setBraceletId(dto.getBraceletId());
        return bracelet;
    }

    @Override
    public BraceletResponseDto mapToDto(Bracelet bracelet) {
        BraceletResponseDto dto = new BraceletResponseDto();
        dto.setBraceletId(bracelet.getBraceletId());
        dto.setResultId(getResultId(bracelet.getResult()));
        return dto;
    }

    private Long getResultId(Result result) {
        return result == null ? -1L : result.getId();
    }
}
