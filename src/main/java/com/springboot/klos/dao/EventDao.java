package com.springboot.klos.dao;

import com.springboot.klos.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventDao extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
