package com.springboot.klos.dao;

import com.springboot.klos.model.Result;
import com.springboot.klos.model.Lap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LapDao extends JpaRepository<Lap, Long> {
    List<Lap> findByResult(Result result);

    boolean existsByResultAndLapNumber(Result result, int lapNumber);
}
