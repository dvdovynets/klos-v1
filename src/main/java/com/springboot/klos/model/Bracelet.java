package com.springboot.klos.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "bracelets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"bracelet_id"})})
public class Bracelet {
    @Id
    @Column(name = "bracelet_id")
    private String braceletId;

    @OneToOne
    @JoinColumn(name = "results_id")
    private Result result;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bracelet)) {
            return false;
        }
        Bracelet bracelet = (Bracelet) obj;
        return Objects.equals(braceletId, bracelet.braceletId)
                && Objects.equals(result, bracelet.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(braceletId, result);
    }
}
