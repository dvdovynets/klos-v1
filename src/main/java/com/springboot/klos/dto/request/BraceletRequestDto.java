package com.springboot.klos.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BraceletRequestDto {
    @NotEmpty(message = "Bracelet id must not be empty")
    private String braceletId;

    private Long resultId;
}
