package com.gold.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class UserContext {
    private final String username;
    private final List<GrantedAuthority> authorities;
}
