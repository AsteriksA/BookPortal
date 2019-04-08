package com.gold.security.auth.jwt.extractor;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    private final static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(@NotBlank String header) {
//        if (StringUtils.isBlank(header)) {
//            throw new AuthenticationServiceException("Authorization header cannot be blank!");
//        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
