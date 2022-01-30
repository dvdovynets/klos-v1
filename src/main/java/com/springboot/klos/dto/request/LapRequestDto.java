package com.springboot.klos.dto.request;

import com.springboot.klos.utils.RegExpUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel(description = "Lap request DTO")
@Data
public class LapRequestDto {
    @ApiModelProperty(value = "Lap number for particular participant")
    @Min(value = 1, message = "Wrong lap number")
    private int lapNumber;

    @ApiModelProperty(value = "Lap time, for example 00:40:31")
    @NotEmpty(message = "Lap time must not be empty")
    @Pattern(regexp = RegExpUtil.TIME_PATTERN,
            message = "Please provide a valid time in format hh:mm:ss")
    private String lapTime;

    @ApiModelProperty(value = "Actual date and time of particular scan, "
            + "for example 21.11.2022 18:40")
    @NotEmpty(message = "Actual time must not be empty")
    @Pattern(regexp = RegExpUtil.DATE_TIME_REGEXP,
            message = "Please provide a valid date in format dd.mm.yyyy hh:mm")
    private String actualTime;

    @ApiModelProperty(value = "Some id for identification of the the person who made a scan")
    private String scannerId;

    @ApiModelProperty(value = "Bracelet physical address")
    @NotEmpty(message = "Bracelet id must not be empty")
    private String braceletId;
}
