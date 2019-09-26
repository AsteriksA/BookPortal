package com.gold.security.jwt;

import com.gold.config.WebSecurityConfig;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@ConfigurationProperties(prefix = "demo.security.jwt")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Qualifier("jwtUserDetailsService")
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    public JwtAuthorizationTokenFilter(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
//        this.userDetailsService = userDetailsService;
//        this.jwtTokenUtil = jwtTokenUtil;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("processing authentication for '{}'", request.getRequestURL());

        String tokenPayload = request.getHeader(WebSecurityConfig.TOKEN_HEADER);

        String username = null;
        String authToken = null;
        if (tokenPayload != null && tokenPayload.startsWith("Bearer ")) {
            authToken = tokenPayload.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("an error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                log.warn("the token is expired and not valid anymore", e);
            }
        } else {
            log.warn("couldn't find bearer string, will ignore the header");
        }

        log.debug("checking authentication for user '{}'", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("security context was null, so authorizing user");
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }

            if (jwtTokenUtil.validateToken(authToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authorized user '{}', setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
