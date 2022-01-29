package com.springboot.klos.dto.request;

import com.springboot.klos.utils.RegExpUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel(description = "Event request DTO")
@Data
public class EventRequestDto {
    @ApiModelProperty(value = "Event name, for example 'KLOS 2022'")
    @NotEmpty(message = "Event name must not be empty")
    private String eventName;

    @ApiModelProperty(value = "The date for the start of the particular event")
    @NotEmpty(message = "Event date must not be empty")
    @Pattern(regexp = RegExpUtil.DATE_REGEXP,
            message = "Please provide a date in format dd.mm.yyyy")
    private String eventDate;
}
