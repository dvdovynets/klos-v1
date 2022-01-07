package com.springboot.klos.dto.response;

import lombok.Data;

@Data
public class ParticipantResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String gender;
    private String dateOfBirth;
    private String city;
    private String email;
    private String password;
    private String phoneNumber;
}
