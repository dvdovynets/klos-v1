package com.springboot.klos.dto.request;

import lombok.Data;

@Data
public class ContactRequestDto {
    private String phoneNumber;
    private String email;
}
