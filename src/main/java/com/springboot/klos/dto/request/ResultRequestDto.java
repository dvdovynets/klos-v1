package com.springboot.klos.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "Result request DTO")
@Data
public class ResultRequestDto {
    @ApiModelProperty(value = "Default status is 'DNS', "
            + "other statuses are 'DNF', 'RUNNING' and 'WINNER'")
    @NotEmpty(message = "Status must not be empty")
    private String status;

    @ApiModelProperty(value = "Number the participant wears during the event")
    private int bib;

    @ApiModelProperty(value = "Participant id to which result is connected")
    @Min(value = 1, message = "Provide valid participant id")
    private Long participantId;

    @ApiModelProperty(value = "Event id to which result is connected")
    @Min(value = 1, message = "Provide valid event id")
    private Long eventId;
}
