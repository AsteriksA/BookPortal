package com.gold.service.interfaces;

import com.gold.dto.Role;

public interface RoleService extends BaseService<Role, Long> {

    Role findByName(String name);
}
