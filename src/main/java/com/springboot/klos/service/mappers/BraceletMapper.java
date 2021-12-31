package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Participant;
import org.springframework.stereotype.Component;

@Component
public class BraceletMapper {
    public Bracelet mapToModel(BraceletRequestDto dto) {
        Bracelet bracelet = new Bracelet();
        bracelet.setBraceletId(dto.getBraceletId());
        return bracelet;
    };

    public BraceletResponseDto mapToDto(Bracelet bracelet) {
        BraceletResponseDto dto = new BraceletResponseDto();
        dto.setBraceletId(bracelet.getBraceletId());
        dto.setParticipantId(getParticipantId(bracelet.getParticipant()));
        return dto;
    }

    private Long getParticipantId(Participant participant) {
        return participant == null ? -1L : participant.getId();
    }
}
