package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.AdminRequestDto;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.model.Participant;
import com.springboot.klos.utils.DateTimePatternUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ParticipantMapper implements GenericMapper<Participant, ParticipantRequestDto, ParticipantResponseDto> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(DateTimePatternUtil.DATE_PATTERN);
    private final PasswordEncoder passwordEncoder;

    public ParticipantMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Participant mapToModel(ParticipantRequestDto dto) {
        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setSurname(dto.getSurname());
        participant.setGender(Participant.Gender.valueOf(dto.getGender()));
        participant.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth(), formatter));
        participant.setCity(dto.getCity());
        participant.setEmail(dto.getEmail());
        participant.setPassword(passwordEncoder.encode(dto.getPassword()));
        participant.setPhoneNumber(dto.getPhoneNumber());
        return participant;
    }

    @Override
    public ParticipantResponseDto mapToDto(Participant participant) {
        ParticipantResponseDto dto = new ParticipantResponseDto();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setSurname(participant.getSurname());
        dto.setGender(participant.getGender().name());
        dto.setDateOfBirth(participant.getDateOfBirth().format(formatter));
        dto.setCity(participant.getCity());
        dto.setEmail(participant.getEmail());
        dto.setPhoneNumber(participant.getPhoneNumber());
        return dto;
    }

    public Participant mapDataToParticipant(ParticipantRequestDto dto, Participant participant) {
        participant.setName(dto.getName());
        participant.setSurname(dto.getSurname());
        participant.setGender(Participant.Gender.valueOf(dto.getGender()));
        participant.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth(), formatter));
        participant.setCity(dto.getCity());
        participant.setPhoneNumber(dto.getPhoneNumber());
        return participant;
    }

    public Participant mapToAdmin(AdminRequestDto dto) {
        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setEmail(dto.getEmail());
        participant.setPassword(passwordEncoder.encode(dto.getPassword()));
        participant.setGender(Participant.Gender.valueOf(dto.getGender()));
        participant.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        return participant;
    }
}
