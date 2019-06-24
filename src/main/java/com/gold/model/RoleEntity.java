package com.gold.model;

import org.springframework.security.core.GrantedAuthority;


public enum RoleEntity implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ANONYMOUS, ROLE_MODERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}

