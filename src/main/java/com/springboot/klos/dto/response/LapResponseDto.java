package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Lap response DTO")
@Data
public class LapResponseDto {
    @ApiModelProperty(value = "Lap id from DB")
    private Long id;

    @ApiModelProperty(value = "Current lap number")
    private int lapNumber;

    @ApiModelProperty(value = "Current lap time, "
            + "for example 00:40:30")
    private String lapTime;

    @ApiModelProperty(value = "Actual date and time of particular scan, "
            + "for example 21.11.2022 18:40")
    private String actualTime;

    @ApiModelProperty(value = "Some id for identification of the the person who made a scan")
    private String scannerId;

    @ApiModelProperty(value = "Id for result to which this lap is connected")
    private Long resultId;

    @ApiModelProperty(value = "Number the participant wears during the event")
    private int bib;
}
