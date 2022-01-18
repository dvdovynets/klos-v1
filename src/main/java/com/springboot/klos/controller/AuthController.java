package com.springboot.klos.controller;

import com.springboot.klos.dto.request.LoginRequestDto;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.JWTAuthResponse;
import com.springboot.klos.security.JwtTokenProvider;
import com.springboot.klos.service.ParticipantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(description = "Controller for registration as user or admin, as well as login")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ParticipantService participantService;
    private final JwtTokenProvider tokenProvider;
    private final Environment environment;

    public AuthController(AuthenticationManager authenticationManager,
                          ParticipantService participantService,
                          JwtTokenProvider tokenProvider,
                          Environment environment) {
        this.authenticationManager = authenticationManager;
        this.participantService = participantService;
        this.tokenProvider = tokenProvider;
        this.environment = environment;
    }

    @ApiOperation(value = "Endpoint for login", notes = "Access level ANY")
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@Valid @RequestBody LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    dto.getEmail(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @ApiOperation(value = "Endpoint for register as User", notes = "Access level ANY")
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody ParticipantRequestDto dto) {
        if (participantService.checkIfEmailExists(dto.getEmail())) {
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(participantService.createParticipant(dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint for creation of default admin and roles",
            notes = "Access level Any")
    @PostMapping("/register/admin-with-roles")
    public ResponseEntity<String> createDefaultAdminAndRoles() {
        if (participantService.checkIfEmailExists(environment.getProperty("admin.email"))) {
            return new ResponseEntity<>("Default Admin and Roles already created.", HttpStatus.BAD_REQUEST);
        }
        participantService.createDefaultAdminAndRoles();
        return new ResponseEntity<>("Admin and Roles successfully created!", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint for register as Admin", notes = "Access level ADMIN")
    @PostMapping("/register/admin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody ParticipantRequestDto dto) {
        if (participantService.checkIfEmailExists(dto.getEmail())) {
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(participantService.createAdmin(dto), HttpStatus.CREATED);
    }
}
