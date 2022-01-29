package com.springboot.klos.controller;

import com.springboot.klos.dto.request.BraceletRequestDto;
import com.springboot.klos.dto.response.BraceletResponseDto;
import com.springboot.klos.service.BraceletService;
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
class BraceletRestControllerTest {
    @MockBean
    private BraceletService braceletService;
    private BraceletRequestDto requestDto;
    private BraceletResponseDto responseDto;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        requestDto = new BraceletRequestDto();
        requestDto.setBraceletId("AA:BB:CC");
        requestDto.setResultId(1L);

        responseDto = new BraceletResponseDto();
        responseDto.setBraceletId("AA:BB:CC");
        responseDto.setResultId(1L);
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createBracelet_ok() {
        Mockito.when(braceletService.createBracelet(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/bracelets")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("braceletId", Matchers.equalTo("AA:BB:CC"))
                .body("resultId", Matchers.equalTo(1));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createBracelet_fieldValidationFails() {
        requestDto.setBraceletId("");
        Mockito.when(braceletService.createBracelet(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/bracelets")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("braceletId", Matchers.equalTo("Bracelet id must not be empty"));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_getAllBracelets_ok() {
        List<BraceletResponseDto> bracelets = List.of(responseDto);

        Mockito.when(braceletService.getAllBracelets()).thenReturn(bracelets);

        RestAssuredMockMvc.when()
                .get("/api/v1/bracelets")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("size()", Matchers.equalTo(1))
                .body("[0].braceletId", Matchers.equalTo("AA:BB:CC"))
                .body("[0].resultId", Matchers.equalTo(1));

    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getAllBracelets_forbiddenForUserRole() {
        List<BraceletResponseDto> bracelets = List.of(responseDto);

        Mockito.when(braceletService.getAllBracelets()).thenReturn(bracelets);

        RestAssuredMockMvc.when()
                .get("/api/v1/bracelets")
                .then()
                .statusCode(403)
                .status(HttpStatus.FORBIDDEN);
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_getBraceletById_ok() {
        Mockito.when(braceletService.getBraceletById("AA:BB:CC")).thenReturn(responseDto);

        RestAssuredMockMvc.when()
                .get("/api/v1/bracelets/AA:BB:CC")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("braceletId", Matchers.equalTo("AA:BB:CC"))
                .body("resultId", Matchers.equalTo(1));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_updateBracelet_ok() {
        Mockito.when(braceletService.updateBracelet(requestDto, "AA:BB:CC"))
                .thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .put("/api/v1/bracelets/AA:BB:CC")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("braceletId", Matchers.equalTo("AA:BB:CC"))
                .body("resultId", Matchers.equalTo(1));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void deleteBracelet() {
        RestAssuredMockMvc.when()
                .delete("/api/v1/bracelets/AA:BB:CC")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body(Matchers.equalTo("Bracelet was deleted successfully!"));
    }
}