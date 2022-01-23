package com.springboot.klos.model;

import com.springboot.klos.utils.DateTimePatternUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 7)
    private Status status;

    @Column(name = "bib")
    private int bib;

    @Column(name = "laps_completed")
    private int lapsCompleted;

    @Column(name = "total_distance")
    private double totalDistance;

    @Column(name="fastest_loop")
    private LocalTime fastestLoop = DateTimePatternUtil.ZERO_LOCAL_TIME;

    @Column(name = "slowest_loop")
    private LocalTime slowestLoop = DateTimePatternUtil.ZERO_LOCAL_TIME;

    @Column(name = "average_loop")
    private LocalTime averageLoop = DateTimePatternUtil.ZERO_LOCAL_TIME;

    @Column(name = "total_time_running")
    private LocalTime totalTimeRunning = DateTimePatternUtil.ZERO_LOCAL_TIME;

    @ManyToOne
    @JoinColumn(name = "participants_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "events_id")
    private Event event;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public enum Status {
        DNS, DNF, RUNNING, WINNER
    }
}
