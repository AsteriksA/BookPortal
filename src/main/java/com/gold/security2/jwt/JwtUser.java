package com.gold.security2.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gold.model.State;
import com.gold.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    @JsonIgnore
    private final UserEntity entity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return entity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !entity.getState().equals(State.BANNED);
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return entity.getState().equals(State.ACTIVATED);
    }
}
