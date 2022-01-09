package com.springboot.klos.controller;

import com.springboot.klos.dto.request.LoginRequestDto;
import com.springboot.klos.dto.request.ParticipantRequestDto;
import com.springboot.klos.dto.response.ParticipantResponseDto;
import com.springboot.klos.model.Participant;
import com.springboot.klos.service.ParticipantService;
import com.springboot.klos.service.RoleService;
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

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ParticipantService participantService;
    private final RoleService roleService;

    public AuthController(AuthenticationManager authenticationManager,
                          ParticipantService participantService,
                          RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.participantService = participantService;
        this.roleService = roleService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User successfully logged in!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody ParticipantRequestDto dto) {
        if (participantService.checkIfEmailExists(dto.getEmail())) {
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(participantService.createParticipant(dto), HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody ParticipantRequestDto dto) {
        if (participantService.checkIfEmailExists(dto.getEmail())) {
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(participantService.createParticipant(dto), HttpStatus.CREATED);
    }

    @PostMapping("/register/roles")
    public ResponseEntity<String> createRoles() {
        roleService.createRoles();
        return new ResponseEntity<>(
                "Roles Admin and User were successfully crated.", HttpStatus.CREATED);
    }
}
