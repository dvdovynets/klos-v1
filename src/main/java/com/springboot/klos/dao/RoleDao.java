package com.springboot.klos.dao;

import com.springboot.klos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName name);
}
