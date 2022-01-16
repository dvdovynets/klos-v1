package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Participant response DTO with additional statistics")
@Data
public class ParticipantResponseDto {
    @ApiModelProperty(value = "Participant id from DB")
    private Long id;

    @ApiModelProperty(value = "Participant name")
    private String name;

    @ApiModelProperty(value = "Participant surname")
    private String surname;

    @ApiModelProperty(value = "Gender one of three types 'MALE', 'FEMALE' and 'OTHER'")
    private String gender;

    @ApiModelProperty(value = "Date of birth, for example 01.01.2001")
    private String dateOfBirth;

    @ApiModelProperty(value = "City of living")
    private String city;

    @ApiModelProperty(value = "Email that is used as login")
    private String email;

    @ApiModelProperty(value = "Password, usually this field is null")
    private String password;

    @ApiModelProperty(value = "Participant phone number")
    private String phoneNumber;
}
