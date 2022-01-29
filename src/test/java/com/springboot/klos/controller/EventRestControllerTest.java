package com.springboot.klos.controller;

import com.springboot.klos.dto.request.EventRequestDto;
import com.springboot.klos.dto.response.EventResponseDto;
import com.springboot.klos.service.EventService;
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
class EventRestControllerTest {
    @MockBean
    private EventService eventService;
    private EventRequestDto requestDto;
    private EventResponseDto responseDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        requestDto = new EventRequestDto();
        requestDto.setEventDate("11.11.2022");
        requestDto.setEventName("KLOS-22");

        responseDto = new EventResponseDto();
        responseDto.setId(12L);
        responseDto.setEventName("KLOS-22");
        responseDto.setEventDate("11.11.2022");
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createEvent_ok() {
        Mockito.when(eventService.createEvent(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/events")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("id", Matchers.equalTo(12))
                .body("eventName", Matchers.equalTo("KLOS-22"))
                .body("eventDate", Matchers.equalTo("11.11.2022"));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_createEvent_forbiddenForUser() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/events")
                .then()
                .statusCode(403)
                .status(HttpStatus.FORBIDDEN);
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_createEvent_fieldValidationFails() {
        requestDto.setEventName("");
        requestDto.setEventDate("1.1");
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/events")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("eventName", Matchers.equalTo("Event name must not be empty"))
                .body("eventDate", Matchers.equalTo("Please provide a date in format dd.mm.yyyy"));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getAllEvents_ok() {
        List<EventResponseDto> events = List.of(responseDto);

        Mockito.when(eventService.getAllEvents()).thenReturn(events);

        RestAssuredMockMvc.when()
                .get("/api/v1/events")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].eventName", Matchers.equalTo("KLOS-22"))
                .body("[0].eventDate", Matchers.equalTo("11.11.2022"));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void test_getEventById_ok() {
        Mockito.when(eventService.getEventById(12L)).thenReturn(responseDto);

        RestAssuredMockMvc.when()
                .get("/api/v1/events/12")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(12))
                .body("eventName", Matchers.equalTo("KLOS-22"))
                .body("eventDate", Matchers.equalTo("11.11.2022"));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_updateEvent_ok() {
        Mockito.when(eventService.updateEvent(requestDto, 12L)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .put("/api/v1/events/12")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(12))
                .body("eventName", Matchers.equalTo("KLOS-22"))
                .body("eventDate", Matchers.equalTo("11.11.2022"));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    void test_deleteEvent_ok() {
        RestAssuredMockMvc.when()
                .delete("/api/v1/events/12")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("Event was deleted successfully!"));
    }
}