package com.springboot.klos.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "Result request DTO")
@Data
public class ResultRequestDto {
    @ApiModelProperty(value = "Default status is 'DNS', "
            + "other statuses are 'DNF', 'RUNNING' and 'WINNER'")
    @NotEmpty(message = "Status must not be empty")
    private String status;

    @ApiModelProperty(value = "Participant id to which result is connected")
    @NotEmpty(message = "Participant is must not be empty")
    private Long participantId;

    @ApiModelProperty(value = "Event id to which result is connected")
    @NotEmpty(message = "Event id must not be empty")
    private Long eventId;
}
