package com.springboot.klos.dto.response;

import lombok.Data;

@Data
public class ContactResponseDto {
    private long id;
    private String phoneNumber;
    private String email;
}
