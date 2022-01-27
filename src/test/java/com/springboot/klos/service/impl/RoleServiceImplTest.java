package com.springboot.klos.service.impl;

import com.springboot.klos.dao.RoleDao;
import com.springboot.klos.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleDao roleDao;

    @Test
    void createDefaultRoles() {
        Role user = new Role();
        user.setName(Role.RoleName.ROLE_USER);
        Role admin = new Role();
        admin.setName(Role.RoleName.ROLE_ADMIN);

        Set<Role> roles = Set.of(user, admin);

        when(roleDao.save(user)).thenReturn(user);
        when(roleDao.save(admin)).thenReturn(admin);

        Set<Role> defaultRoles = roleService.createDefaultRoles();

        assertEquals(roles, defaultRoles);

        verify(roleDao, times(2)).save(any());
    }
}