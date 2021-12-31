package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.model.Participant;
import com.springboot.klos.utils.DateTimePatternUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ParticipantMapper {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.DATE_PATTERN);
    private final ContactMapper contactMapper;

    public ParticipantMapper(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    public ParticipantResponseDto mapToDto(Participant participant) {
        ParticipantResponseDto dto = new ParticipantResponseDto();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setSurname(participant.getSurname());
        dto.setGender(participant.getGender().name());
        dto.setDateOfBirth(participant.getDateOfBirth().format(formatter));
        dto.setCity(participant.getCity());
        dto.setContactId(participant.getContact().getId());
        return dto;
    }

    public Participant mapToModel(ParticipantRequestDto dto) {
        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setSurname(dto.getSurname());
        participant.setGender(Participant.Gender.valueOf(dto.getGender()));
        participant.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth(), formatter));
        participant.setCity(dto.getCity());
        participant.setContact(contactMapper.mapToModel(dto.getContactDto()));
        return participant;
    }
}
