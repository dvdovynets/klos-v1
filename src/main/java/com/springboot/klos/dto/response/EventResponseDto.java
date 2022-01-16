package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Event response DTO")
@Data
public class EventResponseDto {
    @ApiModelProperty(value = "Event id from DB")
    private Long id;

    @ApiModelProperty(value = "Event name")
    private String eventName;

    @ApiModelProperty(value = "Event date, for example 11.11.2022")
    private String eventDate;
}
