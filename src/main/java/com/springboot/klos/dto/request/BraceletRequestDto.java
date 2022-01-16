package com.springboot.klos.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "Bracelet request DTO")
@Data
public class BraceletRequestDto {
    @ApiModelProperty(value = "Physical bracelet address")
    @NotEmpty(message = "Bracelet id must not be empty")
    private String braceletId;

    @ApiModelProperty(value = "Result id to which a bracelet will be connected during "
            + "a particular event")
    private Long resultId;
}
