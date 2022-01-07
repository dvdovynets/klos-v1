package com.springboot.klos.dao;

import com.springboot.klos.model.Event;
import com.springboot.klos.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultDao extends JpaRepository<Result, Long> {
    Optional<Result> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<Result> findByEventAndIsDeleted(Event event, boolean isDeleted);
}
