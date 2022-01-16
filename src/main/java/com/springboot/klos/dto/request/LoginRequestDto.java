package com.springboot.klos.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@ApiModel(description = "Login request DTO")
@Data
public class LoginRequestDto {
    @ApiModelProperty(value = "Email that is used as login")
    @Email(message = "Please provide a valid email")
    private String email;
    private String password;
}
