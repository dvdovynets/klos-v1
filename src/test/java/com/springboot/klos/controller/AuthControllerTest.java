package com.springboot.klos.controller;

import com.springboot.klos.TestSecurityConfig;
import com.springboot.klos.dto.request.AdminRequestDto;
import com.springboot.klos.dto.request.LoginRequestDto;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.security.JwtTokenProvider;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestSecurityConfig.class})
@AutoConfigureMockMvc
class AuthControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private ParticipantService participantService;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void test_authenticateUser_ok() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("u@gmail.com");
        dto.setPassword("password");

        Mockito.when(tokenProvider.generateToken(any())).thenReturn("aa.bb.cc");


        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body("accessToken", Matchers.equalTo("aa.bb.cc"));
    }

    @Test
    void test_createUser_ok() {
        ParticipantRequestDto requestDto = new ParticipantRequestDto();
        requestDto.setName("name");
        requestDto.setSurname("surname");
        requestDto.setEmail("u@gmail.com");
        requestDto.setGender("MALE");
        requestDto.setDateOfBirth("01.01.2001");
        requestDto.setPassword("Passw0rd");
        requestDto.setRepeatPassword("Passw0rd");

        ParticipantResponseDto responseDto = new ParticipantResponseDto();
        responseDto.setId(1L);
        responseDto.setName("name");
        responseDto.setSurname("surname");
        responseDto.setEmail("u@gmail.com");
        responseDto.setGender("MALE");
        responseDto.setDateOfBirth("01.01.2001");

        Mockito.when(participantService.checkIfEmailExists("u@gmail.com")).thenReturn(false);
        Mockito.when(participantService.createParticipant(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/auth/register")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("name"))
                .body("surname", Matchers.equalTo("surname"))
                .body("email", Matchers.equalTo("u@gmail.com"))
                .body("gender", Matchers.equalTo("MALE"))
                .body("dateOfBirth", Matchers.equalTo("01.01.2001"));
    }

    @Test
    void test_createUser_emailAlreadyTaken() {
        ParticipantRequestDto requestDto = new ParticipantRequestDto();
        requestDto.setName("name");
        requestDto.setSurname("surname");
        requestDto.setEmail("u@gmail.com");
        requestDto.setGender("MALE");
        requestDto.setDateOfBirth("01.01.2001");
        requestDto.setPassword("Passw0rd");
        requestDto.setRepeatPassword("Passw0rd");

        Mockito.when(participantService.checkIfEmailExists("u@gmail.com")).thenReturn(true);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/auth/register")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body(Matchers.equalTo("Email is already taken."));
    }

    @Test
    void test_createUser_fieldValidationFails() {
        ParticipantRequestDto requestDto = new ParticipantRequestDto();
        requestDto.setName("name");
        requestDto.setSurname("surname");
        requestDto.setEmail("u@gmailcom");
        requestDto.setGender("MALE");
        requestDto.setPassword("Password");
        requestDto.setRepeatPassword("Password");

        Mockito.when(participantService.checkIfEmailExists("u@gmailcom")).thenReturn(false);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/auth/register")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("dateOfBirth",Matchers.equalTo("Date of birth must not be empty"))
                .body("email", Matchers.equalTo("Please provide valid email address"))
                .body("password", Matchers.equalTo("Password must be at least 8 symbols "
                        + "and have at least one upper case letter, one lover case letter, "
                        + "and one digit"));
    }

    @Test
    void test_createAdmin_ok() {
        AdminRequestDto requestDto = new AdminRequestDto();
        requestDto.setName("name");
        requestDto.setEmail("a@gmail.com");
        requestDto.setGender("MALE");
        requestDto.setDateOfBirth("01.01.2001");
        requestDto.setPassword("Pa$$w0rd");
        requestDto.setRepeatPassword("Pa$$w0rd");

        ParticipantResponseDto responseDto = new ParticipantResponseDto();
        responseDto.setId(1L);
        responseDto.setName("name");
        responseDto.setEmail("a@gmail.com");
        responseDto.setGender("MALE");
        responseDto.setDateOfBirth("01.01.2001");

        Mockito.when(participantService.checkIfEmailExists("a@gmail.com")).thenReturn(false);
        Mockito.when(participantService.createAdmin(requestDto)).thenReturn(responseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/auth/register/admin")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("name"))
                .body("email", Matchers.equalTo("a@gmail.com"))
                .body("gender", Matchers.equalTo("MALE"))
                .body("dateOfBirth", Matchers.equalTo("01.01.2001"));
    }

    @Test
    void test_createAdmin_emailAlreadyTaken() {
        AdminRequestDto requestDto = new AdminRequestDto();
        requestDto.setName("name");
        requestDto.setEmail("a@gmail.com");
        requestDto.setGender("MALE");
        requestDto.setDateOfBirth("01.01.2001");
        requestDto.setPassword("Pa$$w0rd");
        requestDto.setRepeatPassword("Pa$$w0rd");

        Mockito.when(participantService.checkIfEmailExists("a@gmail.com")).thenReturn(true);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/auth/register/admin")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body(Matchers.equalTo("Email is already taken."));
    }

    @Test
    void test_createAdmin_fieldValidationFails() {
        AdminRequestDto requestDto = new AdminRequestDto();
        requestDto.setName("name");
        requestDto.setEmail("a@gmail.com.");
        requestDto.setGender("MALE");
        requestDto.setDateOfBirth("01.2001");
        requestDto.setPassword("Passw0rd");
        requestDto.setRepeatPassword("Passw0rd");

        Mockito.when(participantService.checkIfEmailExists("a@gmail.com.")).thenReturn(false);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("/api/v1/auth/register/admin")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("dateOfBirth",Matchers.equalTo("Please provide a date in format dd.mm.yyyy"))
                .body("email", Matchers.equalTo("Please provide valid email address"))
                .body("password", Matchers.equalTo("Password must be at least 8 symbols "
                        + "and have at least one upper case letter, one lover case letter, "
                        + "one special symbol and one digit"));
    }

    @Test
    void test_createDefaultAdminAndRoles_ok() {
        Mockito.when(participantService.checkIfEmailExists(anyString())).thenReturn(false);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .post("/api/v1/auth/register/admin-with-roles")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body(Matchers.equalTo("Admin and Roles created successfully!"));
    }

    @Test
    void test_createDefaultAdminAndRoles_emailAndRolesAlreadyInDb() {
        Mockito.when(participantService.checkIfEmailExists(anyString())).thenReturn(true);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .post("/api/v1/auth/register/admin-with-roles")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body(Matchers.equalTo("Default Admin and Roles already created."));
    }
}