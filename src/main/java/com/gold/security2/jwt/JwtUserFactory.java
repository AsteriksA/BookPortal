package com.gold.security2.jwt;

import com.gold.model.UserEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(UserEntity entity) {
        return new JwtUser(entity);
    }
}
