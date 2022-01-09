package com.springboot.klos.service.impl;

import com.springboot.klos.dao.RoleDao;
import com.springboot.klos.model.Role;
import com.springboot.klos.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void createRoles() {
        Role user = new Role();
        user.setName(Role.RoleName.ROLE_USER);
        roleDao.save(user);

        Role admin = new Role();
        admin.setName(Role.RoleName.ROLE_ADMIN);
        roleDao.save(admin);
    }
}
