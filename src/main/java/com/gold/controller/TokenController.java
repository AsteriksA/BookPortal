package com.gold.controller;

import com.gold.config.JwtSettings;
import com.gold.config.WebSecurityConfig;
import com.gold.security.auth.jwt.extractor.TokenExtractor;
import com.gold.security.auth.jwt.verifier.TokenVerifier;
import com.gold.security.exceptions.InvalidJwtToken;
import com.gold.security.model.UserContext;
import com.gold.security.model.token.JwtToken;
import com.gold.security.model.token.JwtTokenFactory;
import com.gold.security.model.token.RawAccessJwtToken;
import com.gold.security.model.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenController {

    private final JwtTokenFactory tokenFactory;
    private final JwtSettings jwtSettings;
    private final UserDetailsService userDetailsService;
    private final TokenVerifier tokenVerifier;
    private final TokenExtractor tokenExtractor;

    @GetMapping("/api/auth/token")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey())
                .orElseThrow(InvalidJwtToken::new);
        String jti = refreshToken.getClaims().getBody().getId();
        checkJti(jti);

        String subject = refreshToken.getClaims().getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();

        isAuthoritiesNull(authorities);

        UserContext userContext = UserContext.create(userDetails.getUsername(),authorities);

        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken newRefreshToken = tokenFactory.createRefreshToken(userContext);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken.getToken());
        tokenMap.put("refreshToken", newRefreshToken.getToken());

        return tokenMap;
    }

    private void isAuthoritiesNull(List<GrantedAuthority> authorities) {
        if (authorities == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }
    }

    private void checkJti(String jti) throws InvalidJwtToken {
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }
    }
}
