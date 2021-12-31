package com.springboot.klos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "bracelets",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"bracelet_id", "participants_id"})})
public class Bracelet {
    @Id
    @Column(name = "bracelet_id")
    private String braceletId;

    @OneToOne
    @JoinColumn(name = "participants_id")
    private Participant participant;

}
