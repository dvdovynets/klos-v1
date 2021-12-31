package com.springboot.klos.dao;

import com.springboot.klos.model.Lap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LapDao extends JpaRepository<Lap, Long> {
}
