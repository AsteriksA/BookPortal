package com.gold.service.interfaces;

import com.gold.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Role getRoleByName(String name);

    Role getRoleById(Long id);

    void addRole(Role role);

    void removeRole(Long id);
}
