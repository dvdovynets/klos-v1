package com.springboot.klos.dto.request;

import com.springboot.klos.lib.FieldsValueMatch;
import com.springboot.klos.utils.RegExpUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "Participant request DTO")
@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class ParticipantRequestDto {
    @ApiModelProperty(value = "Participant name")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @ApiModelProperty(value = "Participant surname")
    @NotEmpty(message = "Surname must not be empty")
    private String surname;

    @ApiModelProperty(value = "Available three genders 'MALE', 'FEMALE' and 'OTHER'")
    @NotEmpty(message = "Please select a gender")
    private String gender;

    @ApiModelProperty(value = "Date of birth, for example 01.01.2001")
    @Pattern(regexp = RegExpUtil.DATE_REGEXP,
            message = "Please provide a date in format dd.mm.yyyy")
    private String dateOfBirth;

    @ApiModelProperty(value = "City of living")
    private String city;

    @ApiModelProperty(value = "Email that will be used as login in application, "
            + "for example d@gmail.com")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Please provide valid email address")
    private String email;

    @ApiModelProperty(value = "Password, at least 6 symbols 256 bit")
    @NotEmpty(message = "Password must not be empty")
    @Pattern(regexp = RegExpUtil.PASSWORD_REGEXP,
            message = "Password must be at least 6 symbols "
                    + "and have at least one upper case letter, "
                    + "one lover case letter,"
                    + "one special symbol"
                    + "and one digit")
    private String password;

    @ApiModelProperty(value = "Just repeat password")
    @NotEmpty(message = "Repeat password must not be empty")
    private String repeatPassword;

    @ApiModelProperty(value = "Phone number")
    private String phoneNumber;
}
