package com.springboot.klos.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event event = (Event) obj;
        return isDeleted == event.isDeleted
                && Objects.equals(id, event.id)
                && Objects.equals(eventName, event.eventName)
                && Objects.equals(eventDate, event.eventDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, eventDate, isDeleted);
    }
}
