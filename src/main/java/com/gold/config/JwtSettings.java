package com.gold.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "demo.security.jwt")
public class JwtSettings {
    private String tokenIssuer;
    private String tokenSigningKey;
    private Long tokenExpirationTime;
    private Long refreshTokenExpTime;
}
