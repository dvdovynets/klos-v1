package com.springboot.klos.controller;

import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.service.ParticipantService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ParticipantRestControllerTest {
    @MockBean
    private ParticipantService participantService;
    private ParticipantRequestDto requestDto;
    private ParticipantResponseDto responseDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        requestDto = new ParticipantRequestDto();
        requestDto.setName("name");
        requestDto.setSurname("surname");
        requestDto.setDateOfBirth("01.01.2001");
        requestDto.setGender("FEMALE");
        requestDto.setPassword("Passw0rd");
        requestDto.setRepeatPassword("Passw0rd");
        requestDto.setEmail("user@gmail.com");

        responseDto = new ParticipantResponseDto();
        responseDto.setId(21L);
        responseDto.setName("name");
        responseDto.setSurname("surname");
        responseDto.setDateOfBirth("01.01.2001");
        responseDto.setGender("FEMALE");
        responseDto.setEmail("user@gmail.com");
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getAllParticipants_ok() {
        List<ParticipantResponseDto> participants = List.of(responseDto);
        Mockito.when(participantService.getAllParticipants()).thenReturn(participants);

        RestAssuredMockMvc.when()
                .get("/api/v1/participants")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(21))
                .body("[0].email", Matchers.equalTo("user@gmail.com"))
                .body("[0].name", Matchers.equalTo("name"))
                .body("[0].surname", Matchers.equalTo("surname"))
                .body("[0].dateOfBirth", Matchers.equalTo("01.01.2001"))
                .body("[0].gender", Matchers.equalTo("FEMALE"))
                .body("[0].email", Matchers.equalTo("user@gmail.com"))
                .body("[0].password", Matchers.nullValue());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getParticipantById_ok() {
        Mockito.when(participantService.getParticipantById(21L)).thenReturn(responseDto);

        RestAssuredMockMvc.when()
                .get("/api/v1/participants/21")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(21))
                .body("email", Matchers.equalTo("user@gmail.com"))
                .body("name", Matchers.equalTo("name"))
                .body("surname", Matchers.equalTo("surname"))
                .body("dateOfBirth", Matchers.equalTo("01.01.2001"))
                .body("gender", Matchers.equalTo("FEMALE"))
                .body("email", Matchers.equalTo("user@gmail.com"))
                .body("password", Matchers.nullValue());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_updateParticipant_ok() {
        Mockito.when(participantService.updateParticipant(requestDto, 21L)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .put("/api/v1/participants/21")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(21))
                .body("email", Matchers.equalTo("user@gmail.com"))
                .body("name", Matchers.equalTo("name"))
                .body("surname", Matchers.equalTo("surname"))
                .body("dateOfBirth", Matchers.equalTo("01.01.2001"))
                .body("gender", Matchers.equalTo("FEMALE"))
                .body("email", Matchers.equalTo("user@gmail.com"))
                .body("password", Matchers.nullValue());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_deleteParticipant_ok() {
        RestAssuredMockMvc.when()
                .delete("/api/v1/participants/21")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body(Matchers.equalTo("Participant was deleted successfully!"));
    }
}