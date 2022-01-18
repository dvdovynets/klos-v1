package com.springboot.klos.service;

import com.springboot.klos.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> createDefaultRoles();
}
