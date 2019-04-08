package com.gold.security.auth.ajax;

import com.gold.security.model.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;
    private final UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
        String name = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails userCandidate = userDetailsService.loadUserByUsername(name);

        checkUserDetails(password, userCandidate);

        List<GrantedAuthority > authorities = (List<GrantedAuthority>) userCandidate.getAuthorities();
        UserContext userContext = UserContext.create(name, authorities);
        return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
    }

    private void checkUserDetails(String password, UserDetails userCandidate) {
        if (!encoder.matches(password, userCandidate.getPassword())) {
            log.warn("Authentication Failed. Username or Password not valid.");
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }
        if (userCandidate.getAuthorities() == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
