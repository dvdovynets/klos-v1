package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Represents the result for each participant for each event he is running")
@Data
public class ResultResponseDto {
    @ApiModelProperty(value = "Result id from DB")
    private Long resultId;

    @ApiModelProperty(value = "Current status. Could be 'DNS', 'DNF', 'RUNNING' and 'WINNER'")
    private String status;

    @ApiModelProperty(value = "Number the participant wears during the event")
    private int bib;

    @ApiModelProperty(value = "Participant full name")
    private String participantFullName;

    @ApiModelProperty(value = "Participant gender. Could be 'MALE', 'FEMALE' and 'OTHER'")
    private String gender;

    @ApiModelProperty(value = "How many laps completed")
    private int lapsCompleted;

    @ApiModelProperty(value = "Total distance for current race")
    private Double totalDistance;

    @ApiModelProperty(value = "The fastest loop for current race")
    private String fastestLoop;

    @ApiModelProperty(value = "The slowest loop for current race")
    private String slowestLoop;

    @ApiModelProperty(value = "The average loop for current race")
    private String averageLoop;

    @ApiModelProperty(value = "Total time participant is running for current race")
    private String totalTimeRunning;

    @ApiModelProperty(value = "Participant id")
    private Long participantId;

    @ApiModelProperty(value = "Current event id")
    private Long eventId;
}
