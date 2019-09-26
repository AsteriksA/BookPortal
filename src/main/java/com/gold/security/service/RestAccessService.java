package com.gold.security.service;

import com.gold.security.jwt.JwtUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RestAccessService {

    public boolean isExistedUser(Long userId, Authentication authentication) {
        JwtUser user = (JwtUser) authentication.getPrincipal();
        if (!Objects.equals(userId, user.getEntity().getId())) {
            throw new AccessDeniedException("Access is denied. You cannot change another account");
        }
        return true;
    }
}
