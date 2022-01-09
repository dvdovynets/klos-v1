package com.springboot.klos.dao;

import com.springboot.klos.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantDao extends JpaRepository<Participant, Long> {
    Optional<Participant> findByIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<Participant> findByEmailAndIsDeleted(String email, boolean isDeleted);

    boolean existsByEmail(String email);
}
