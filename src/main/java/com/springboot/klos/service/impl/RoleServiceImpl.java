package com.springboot.klos.service.impl;

import com.springboot.klos.dao.RoleDao;
import com.springboot.klos.model.Role;
import com.springboot.klos.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> createDefaultRoles() {
        Set<Role> roles = new HashSet<>();

        Role user = new Role();
        user.setName(Role.RoleName.ROLE_USER);
        roles.add(roleDao.save(user));

        Role admin = new Role();
        admin.setName(Role.RoleName.ROLE_ADMIN);
        roles.add(roleDao.save(admin));

        return roles;
    }
}
