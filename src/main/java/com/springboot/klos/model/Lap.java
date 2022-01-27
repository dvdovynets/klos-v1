package com.springboot.klos.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "laps")
public class Lap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lap_number")
    private int lapNumber;

    @Column(name = "lap_time")
    private LocalTime lapTime;

    @Column(name = "actual_time")
    private LocalDateTime actualTime;

    @Column(name = "scanner_id")
    private String scannerId;

    @ManyToOne
    @JoinColumn(name = "results_id")
    private Result result;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Lap)) {
            return false;
        }
        Lap lap = (Lap) obj;
        return lapNumber == lap.lapNumber
                && Objects.equals(id, lap.id)
                && Objects.equals(lapTime, lap.lapTime)
                && Objects.equals(actualTime, lap.actualTime)
                && Objects.equals(scannerId, lap.scannerId)
                && Objects.equals(result, lap.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lapNumber, lapTime, actualTime, scannerId, result);
    }
}
