package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Bracelet response DTO")
@Data
public class BraceletResponseDto {
    @ApiModelProperty(value = "Bracelet physical address")
    private String braceletId;

    @ApiModelProperty(value = "Result id")
    private Long resultId;
}
