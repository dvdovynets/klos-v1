package com.springboot.klos.dao;

import com.springboot.klos.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantDao extends JpaRepository<Participant, Long> {
}
