package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.AdminRequestDto;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.model.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantMapperTest {
    @InjectMocks
    private ParticipantMapper participantMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    private AdminRequestDto adminRequest;
    private ParticipantRequestDto requestDto;
    private ParticipantResponseDto responseDto;
    private Participant user;
    private Participant admin;

    @BeforeEach
    void setUp() {
        adminRequest = new AdminRequestDto();
        adminRequest.setName("admin");
        adminRequest.setEmail("e@g.com");
        adminRequest.setGender("MALE");
        adminRequest.setPassword("1234");
        adminRequest.setRepeatPassword("1234");
        adminRequest.setDateOfBirth("01.01.2001");

        requestDto = new ParticipantRequestDto();
        requestDto.setName("user");
        requestDto.setSurname("user");
        requestDto.setCity("city");
        requestDto.setEmail("u@g.com");
        requestDto.setGender("FEMALE");
        requestDto.setDateOfBirth("02.02.2002");
        requestDto.setPassword("123");
        requestDto.setRepeatPassword("123");
        requestDto.setPhoneNumber("321");

        responseDto = new ParticipantResponseDto();
        responseDto.setId(1L);
        responseDto.setName("user");
        responseDto.setSurname("user");
        responseDto.setCity("city");
        responseDto.setEmail("u@g.com");
        responseDto.setGender("FEMALE");
        responseDto.setDateOfBirth("02.02.2002");
        responseDto.setPhoneNumber("321");

        user = new Participant();
        user.setName("user");
        user.setSurname("user");
        user.setCity("city");
        user.setEmail("u@g.com");
        user.setGender(Participant.Gender.FEMALE);
        user.setDateOfBirth(LocalDate.of(2002, 2, 2));
        user.setPassword("123");
        user.setPhoneNumber("321");

        admin = new Participant();
        admin.setName("admin");
        admin.setEmail("e@g.com");
        admin.setGender(Participant.Gender.MALE);
        admin.setDateOfBirth(LocalDate.of(2001, 1, 1));
    }

    @Test
    void mapToModel() {
        when(passwordEncoder.encode("123")).thenReturn("123");
        Participant mappedUser = participantMapper.mapToModel(requestDto);
        assertEquals(user, mappedUser);
    }

    @Test
    void mapToDto() {
        user.setId(1L);
        ParticipantResponseDto mappedResponse = participantMapper.mapToDto(user);
        assertEquals(responseDto, mappedResponse);
    }

    @Test
    void mapDataToParticipant() {
        Participant mappedUser = participantMapper.mapDataToParticipant(requestDto, new Participant());
        mappedUser.setEmail("u@g.com");
        mappedUser.setPassword("123");
        assertEquals(user, mappedUser);
    }

    @Test
    void mapToAdmin() {
        Participant mappedAdmin = participantMapper.mapToAdmin(adminRequest);
        assertEquals(admin, mappedAdmin);
    }
}