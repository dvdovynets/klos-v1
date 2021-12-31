package com.springboot.klos.dto.response;

import lombok.Data;

@Data
public class ParticipantResponseDto {
    private long id;
    private String name;
    private String surname;
    private String gender;
    private String dateOfBirth;
    private String city;
    private Long contactId;
}
