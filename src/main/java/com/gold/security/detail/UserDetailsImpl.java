package com.gold.security.detail;

import com.gold.model.State;
import com.gold.model.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter
//@RequiredArgsConstructor(staticName = "create")
public class UserDetailsImpl implements UserDetails {

    private final UserEntity entity;

    public UserDetailsImpl(UserEntity entity) {
        this.entity = entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return entity.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @Override
    public String getUsername() {
        return entity.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !entity.getState().equals(State.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return entity.getState().equals(State.ACTIVATED);
    }
}
