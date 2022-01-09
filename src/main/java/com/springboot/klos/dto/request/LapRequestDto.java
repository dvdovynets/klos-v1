package com.springboot.klos.dto.request;

import com.springboot.klos.utils.RegExpUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class LapRequestDto {
    @NotEmpty(message = "Lap number must not be empty")
    private int lapNumber;

    @Pattern(regexp = RegExpUtil.TIME_PATTERN,
            message = "Please provide a valid time in format hh:mm:ss")
    private String lapTime;

    @Pattern(regexp = RegExpUtil.DATE_TIME_REGEXP,
            message = "Please provide a valid date in format dd.mm.yyyy hh:mm")
    private String actualTime;

    private String scannerId;

    @NotEmpty(message = "Bracelet id must not be empty")
    private String braceletId;

    @NotEmpty(message = "Event id must not be empty")
    private Long eventId;
}
