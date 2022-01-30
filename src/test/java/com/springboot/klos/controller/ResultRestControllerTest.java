package com.springboot.klos.controller;

import com.springboot.klos.TestSecurityConfig;
import com.springboot.klos.dto.request.ResultRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.dto.response.ResultResponseDto;
import com.springboot.klos.service.ResultService;
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
@SpringBootTest(classes = {TestSecurityConfig.class})
@AutoConfigureMockMvc
class ResultRestControllerTest {
    @MockBean
    private ResultService resultService;
    private ResultRequestDto requestDto;
    private ResultResponseDto responseDto;
    private LapResponseDto lapResponseDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        requestDto = new ResultRequestDto();
        requestDto.setEventId(1L);
        requestDto.setBib(333);
        requestDto.setParticipantId(2L);
        requestDto.setStatus("DNS");

        responseDto = new ResultResponseDto();
        responseDto.setResultId(1L);
        responseDto.setStatus("DNS");
        responseDto.setBib(333);
        responseDto.setEventId(1L);
        responseDto.setParticipantId(2L);

        lapResponseDto = new LapResponseDto();
        lapResponseDto.setId(1L);
        lapResponseDto.setLapNumber(2);
        lapResponseDto.setLapTime("00:40:00");
        lapResponseDto.setScannerId("123456");
        lapResponseDto.setBib(333);
        lapResponseDto.setResultId(1L);
        lapResponseDto.setActualTime("11.11.2022 18:40");
    }

    @Test
    void test_createResult_ok() {
        Mockito.when(resultService.createResult(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/results")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("resultId", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("DNS"))
                .body("bib", Matchers.equalTo(333))
                .body("participantId", Matchers.equalTo(2))
                .body("eventId", Matchers.equalTo(1));
    }

    @Test
    void test_createResult_fieldValidationFails() {
        requestDto.setEventId(0L);
        requestDto.setParticipantId(-1L);
        requestDto.setStatus("");

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/results")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("status", Matchers.equalTo("Status must not be empty"))
                .body("participantId", Matchers.equalTo("Provide valid participant id"))
                .body("eventId", Matchers.equalTo("Provide valid event id"));
    }

    @Test
    void test_getAllResults_ok() {
        List<ResultResponseDto> results = List.of(responseDto);

        Mockito.when(resultService.getAllResults(0L)).thenReturn(results);

        RestAssuredMockMvc.when()
                .get("/api/v1/results")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("[0].resultId", Matchers.equalTo(1))
                .body("[0].status", Matchers.equalTo("DNS"))
                .body("[0].bib", Matchers.equalTo(333))
                .body("[0].participantId", Matchers.equalTo(2))
                .body("[0].eventId", Matchers.equalTo(1));
    }

    @Test
    void test_getAllLapsForResult_ok() {
        List<LapResponseDto> laps = List.of(lapResponseDto);

        Mockito.when(resultService.getAllLapsForResult(1L)).thenReturn(laps);

        RestAssuredMockMvc.when()
                .get("/api/v1/results/1/laps")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].bib", Matchers.equalTo(333))
                .body("[0].resultId", Matchers.equalTo(1))
                .body("[0].lapNumber", Matchers.equalTo(2));
    }

    @Test
    void test_getResultById_ok() {
        Mockito.when(resultService.getResultById(1L)).thenReturn(responseDto);

        RestAssuredMockMvc.when()
                .get("/api/v1/results/1")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("resultId", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("DNS"))
                .body("bib", Matchers.equalTo(333))
                .body("participantId", Matchers.equalTo(2))
                .body("eventId", Matchers.equalTo(1));
    }

    @Test
    void test_updateResult_ok() {
        Mockito.when(resultService.updateResult(requestDto, 1L)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .put("/api/v1/results/1")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("resultId", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("DNS"))
                .body("bib", Matchers.equalTo(333))
                .body("participantId", Matchers.equalTo(2))
                .body("eventId", Matchers.equalTo(1));
    }

    @Test
    void test_deleteResult_ok() {
        RestAssuredMockMvc.when()
                .delete("/api/v1/results/1")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body(Matchers.equalTo("Result was deleted successfully!"));
    }
}