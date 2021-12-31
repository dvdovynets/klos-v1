package com.springboot.klos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "laps")
public class Lap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lap_number")
    private int lapNumber;

    @Column(name = "lap_time")
    private LocalDateTime lapTime;

    @Column(name = "actual_time")
    private LocalDateTime actualTime;

    @Column(name = "scanner_id")
    private String scannerId;

    @ManyToOne
    @JoinColumn(name = "participants_id")
    private Participant participant;
}
