package com.gold.service.interfaces;

import com.gold.model.Role;

import java.util.List;

public interface RoleService extends BaseService<Role, Long> {

    Role findByName(String name);
}
