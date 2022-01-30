package com.springboot.klos.controller;

import com.springboot.klos.dto.request.LapRequestDto;
import com.springboot.klos.dto.response.LapResponseDto;
import com.springboot.klos.exception.KLOSApiException;
import com.springboot.klos.service.LapService;
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
class LapRestControllerTest {
    @MockBean
    private LapService lapService;
    private LapRequestDto requestDto;
    private LapResponseDto responseDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        requestDto = new LapRequestDto();
        requestDto.setLapNumber(3);
        requestDto.setLapTime("00:40:00");
        requestDto.setActualTime("11.11.2022 18:40");
        requestDto.setBraceletId("AA:BB:CC");
        requestDto.setScannerId("123456");

        responseDto = new LapResponseDto();
        responseDto.setId(1L);
        responseDto.setBib(222);
        responseDto.setResultId(5L);
        responseDto.setLapTime("00:40:00");
        responseDto.setActualTime("11.11.2022 18:40");
        responseDto.setScannerId("123456");
        responseDto.setLapNumber(3);
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createLap_ok() {
        Mockito.when(lapService.createLap(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/laps")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("id", Matchers.equalTo(1))
                .body("bib", Matchers.equalTo(222))
                .body("resultId", Matchers.equalTo(5))
                .body("lapTime", Matchers.equalTo("00:40:00"))
                .body("actualTime", Matchers.equalTo("11.11.2022 18:40"))
                .body("scannerId", Matchers.equalTo("123456"))
                .body("lapNumber", Matchers.equalTo(3));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createLap_fieldValidationFails() {
        requestDto.setLapNumber(-1);
        requestDto.setLapTime("00:400:00");
        requestDto.setActualTime("11.30.2022 166:40");
        requestDto.setBraceletId("");

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/laps")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("lapTime",
                        Matchers.equalTo("Please provide a valid time in format hh:mm:ss"))
                .body("actualTime",
                        Matchers.equalTo("Please provide a valid date in format dd.mm.yyyy hh:mm"))
                .body("braceletId", Matchers.equalTo("Bracelet id must not be empty"))
                .body("lapNumber", Matchers.equalTo("Wrong lap number"));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createLap_lapsIsAlreadyExistsInDb() {
        Mockito.when(lapService.createLap(requestDto)).thenThrow(KLOSApiException.class);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/laps")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body(Matchers.equalTo("Lap is already exists in DB."));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_createLap_forbiddenForUserRole() {
        Mockito.when(lapService.createLap(requestDto)).thenThrow(KLOSApiException.class);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/laps")
                .then()
                .statusCode(403)
                .status(HttpStatus.FORBIDDEN);
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getAllLaps_ok() {
        List<LapResponseDto> laps = List.of(responseDto);

        Mockito.when(lapService.getAllLaps()).thenReturn(laps);

        RestAssuredMockMvc.when()
                .get("/api/v1/laps")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].bib", Matchers.equalTo(222))
                .body("[0].resultId", Matchers.equalTo(5))
                .body("[0].lapTime", Matchers.equalTo("00:40:00"))
                .body("[0].actualTime", Matchers.equalTo("11.11.2022 18:40"))
                .body("[0].scannerId", Matchers.equalTo("123456"))
                .body("[0].lapNumber", Matchers.equalTo(3));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getLapById_ok() {
        Mockito.when(lapService.getLapById(1L)).thenReturn(responseDto);

        RestAssuredMockMvc.when()
                .get("/api/v1/laps/1")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(1))
                .body("bib", Matchers.equalTo(222))
                .body("resultId", Matchers.equalTo(5))
                .body("lapTime", Matchers.equalTo("00:40:00"))
                .body("actualTime", Matchers.equalTo("11.11.2022 18:40"))
                .body("scannerId", Matchers.equalTo("123456"))
                .body("lapNumber", Matchers.equalTo(3));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_updateLap_ok() {
        Mockito.when(lapService.updateLap(requestDto, 1L)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .put("/api/v1/laps/1")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(1))
                .body("bib", Matchers.equalTo(222))
                .body("resultId", Matchers.equalTo(5))
                .body("lapTime", Matchers.equalTo("00:40:00"))
                .body("actualTime", Matchers.equalTo("11.11.2022 18:40"))
                .body("scannerId", Matchers.equalTo("123456"))
                .body("lapNumber", Matchers.equalTo(3));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_deleteLap_ok() {
        RestAssuredMockMvc.when()
                .delete("/api/v1/laps/1")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body(Matchers.equalTo("Lap was deleted successfully!"));
    }
}