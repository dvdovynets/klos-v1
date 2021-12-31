package com.springboot.klos.dao;

import com.springboot.klos.model.Bracelet;
import com.springboot.klos.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BraceletDao extends JpaRepository<Bracelet, String> {
}
