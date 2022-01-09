package com.springboot.klos.dto.request;

import com.springboot.klos.utils.RegExpUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class EventRequestDto {
    @NotEmpty(message = "Event name must not be empty")
    private String eventName;

    @Pattern(regexp = RegExpUtil.DATE_REGEXP,
            message = "Please provide a date in format dd.mm.yyyy")
    private String eventDate;
}
