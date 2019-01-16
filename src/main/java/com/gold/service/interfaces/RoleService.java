package com.gold.service.interfaces;

import com.gold.dto.RoleDto;
import com.gold.model.Role;

public interface RoleService extends BaseService<RoleDto, Long> {

    RoleDto findByName(String name);
}
