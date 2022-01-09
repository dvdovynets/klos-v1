package com.springboot.klos.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginRequestDto {
    @Email(message = "Please provide a valid email")
    private String email;
    private String password;
}
