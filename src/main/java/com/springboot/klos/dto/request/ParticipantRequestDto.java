package com.springboot.klos.dto.request;

import com.springboot.klos.lib.FieldsValueMatch;
import com.springboot.klos.utils.RegExpUtil;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class ParticipantRequestDto {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Surname must not be empty")
    private String surname;

    @NotEmpty(message = "Please select a gender")
    private String gender;

    @Pattern(regexp = RegExpUtil.DATE_REGEXP,
            message = "Please provide a date in format dd.mm.yyyy")
    private String dateOfBirth;

    private String city;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Please provide valid email address")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 symbols")
    private String password;

    @NotEmpty(message = "Repeat password must not be empty")
    private String repeatPassword;

    private String phoneNumber;
}
