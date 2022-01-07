package com.springboot.klos.dto.request;

import lombok.Data;

@Data
public class ParticipantRequestDto {
    private String name;
    private String surname;
    private String gender;
    private String dateOfBirth;
    private String city;
    private String email;
    private String password;
    private String repeatPassword;
    private String phoneNumber;
}
