package com.springboot.klos.service.impl;

import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.dao.RoleDao;
import com.springboot.klos.dto.request.AdminRequestDto;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.model.Participant;
import com.springboot.klos.model.Role;
import com.springboot.klos.service.RoleService;
import com.springboot.klos.service.mappers.ParticipantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String EMAIL = "email@gmail.com";
    private static final Participant.Gender GENDER = Participant.Gender.MALE;
    private Role roleUser;
    private Role roleAdmin;
    private Participant participant;
    private ParticipantRequestDto requestDto;
    private ParticipantResponseDto responseDto;
    private AdminRequestDto adminRequestDto;

    @InjectMocks
    private ParticipantServiceImpl participantService;
    @Mock
    private ParticipantDao participantDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private RoleService roleService;
    @Mock
    private ParticipantMapper participantMapper;
    @Mock
    private Environment environment;

    @BeforeEach
    void setUp() {
        roleUser = new Role();
        roleUser.setName(Role.RoleName.ROLE_USER);
        roleAdmin = new Role();
        roleAdmin.setName(Role.RoleName.ROLE_ADMIN);

        participant = new Participant();
        participant.setId(1L);
        participant.setName(NAME);
        participant.setSurname(SURNAME);
        participant.setEmail(EMAIL);
        participant.setGender(GENDER);
        participant.setRoles(Set.of(roleUser, roleAdmin));

        requestDto = new ParticipantRequestDto();
        requestDto.setName(NAME);
        requestDto.setSurname(SURNAME);
        requestDto.setEmail(EMAIL);
        requestDto.setGender(GENDER.name());

        responseDto = new ParticipantResponseDto();
        responseDto.setName(NAME);
        responseDto.setSurname(SURNAME);
        responseDto.setEmail(EMAIL);
        responseDto.setGender(GENDER.name());

        adminRequestDto = new AdminRequestDto();
        adminRequestDto.setName(NAME);
        adminRequestDto.setEmail(EMAIL);
        adminRequestDto.setGender(GENDER.name());
    }

    @Test
    void createParticipant() {
        when(roleDao.findByName(any())).thenReturn(Optional.of(roleUser));
        when(participantDao.save(participant)).thenReturn(participant);
        when(participantMapper.mapToModel(requestDto)).thenReturn(participant);
        when(participantMapper.mapToDto(participant)).thenReturn(responseDto);

        ParticipantResponseDto participantFromDb = participantService.createParticipant(requestDto);

        assertEquals(responseDto, participantFromDb);

        verify(participantDao).save(participant);
    }

    @Test
    void getAllParticipants() {
        List<Participant> participants = List.of(participant);
        when(participantDao.findAll()).thenReturn(participants);
        when(participantMapper.mapToDto(any())).thenReturn(responseDto);

        List<ParticipantResponseDto> participantsFromDb = participantService.getAllParticipants();

        assertEquals(participants.size(), participantsFromDb.size());

        verify(participantDao).findAll();
    }

    @Test
    void getParticipantById() {
        when(participantDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(participant));
        when(participantMapper.mapToDto(participant)).thenReturn(responseDto);

        ParticipantResponseDto participantFromDb = participantService.getParticipantById(1L);

        assertEquals(responseDto, participantFromDb);

        verify(participantDao).findByIdAndIsDeleted(anyLong(), anyBoolean());
    }

    @Test
    void updateParticipant() {
        when(participantDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(participant));
        when(participantMapper.mapToDto(participant)).thenReturn(responseDto);
        when(participantMapper.mapDataToParticipant(requestDto, participant)).thenReturn(participant);
        when(participantDao.save(participant)).thenReturn(participant);

        ParticipantResponseDto participantFromDb =
                participantService.updateParticipant(requestDto, 1L);

        assertEquals(responseDto, participantFromDb);

        verify(participantDao).save(participant);
    }

    @Test
    void deleteParticipant() {
        when(participantDao.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(participant));
        when(participantDao.save(participant)).thenReturn(participant);

        participantService.deleteParticipant(1L);

        verify(participantDao).save(participant);
    }

    @Test
    void checkIfEmailExists() {
        when(participantDao.existsByEmail(EMAIL)).thenReturn(true);

        assertTrue(participantService.checkIfEmailExists(EMAIL));
    }

    @Test
    void createAdmin() {
        when(roleDao.findByName(Role.RoleName.ROLE_ADMIN)).thenReturn(Optional.of(roleAdmin));
        when(roleDao.findByName(Role.RoleName.ROLE_USER)).thenReturn(Optional.of(roleUser));
        when(participantMapper.mapToAdmin(adminRequestDto)).thenReturn(participant);
        when(participantMapper.mapToDto(participant)).thenReturn(responseDto);
        when(participantDao.save(participant)).thenReturn(participant);

        ParticipantResponseDto adminFromDb = participantService.createAdmin(adminRequestDto);

        assertEquals(responseDto, adminFromDb);

        verify(participantDao).save(participant);
    }

    @Test
    void createDefaultAdminAndRoles() {
        when(environment.getProperty("admin.name")).thenReturn(NAME);
        when(environment.getProperty("admin.email")).thenReturn(EMAIL);
        when(environment.getProperty("admin.gender")).thenReturn(GENDER.name());
        when(environment.getProperty("admin.dateOfBirth")).thenReturn("01.01.2001");
        when(environment.getProperty("admin.password")).thenReturn("Password");
        when(participantMapper.mapToAdmin(any())).thenReturn(participant);
        when(roleService.createDefaultRoles()).thenReturn(Set.of(roleAdmin, roleUser));

        participantService.createDefaultAdminAndRoles();

        verify(roleService).createDefaultRoles();
        verify(participantDao).save(participant);
    }
}