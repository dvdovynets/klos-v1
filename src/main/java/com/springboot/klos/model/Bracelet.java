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

@Getter
@Setter
@Entity
@Table(name = "bracelets",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"bracelet_id"})})
public class Bracelet {
    @Id
    @Column(name = "bracelet_id")
    private String braceletId;

    @OneToOne
    @JoinColumn(name = "results_id")
    private Result result;
}
