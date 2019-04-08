package com.gold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gold.app.CustomCorsFilter;
import com.gold.security.RestAuthenticationEntryPoint;
import com.gold.security.auth.ajax.AjaxAuthenticationProvider;
import com.gold.security.auth.ajax.AjaxLoginProcessingFilter;
import com.gold.security.auth.jwt.JwtAuthenticationProvider;
import com.gold.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.gold.security.auth.jwt.SkipPathRequestMatcher;
import com.gold.security.auth.jwt.extractor.TokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    private static final String API_ROOT_URL = "/api/**";
    private static final String ADMIN_URL = "/api/admin/*";
    private static final String USER_URL = "/api/books";
    private static final String AUTHENTICATION_URL = "/api/auth/login";
    private static final String SIGN_UP_URL = "/api/auth/signup";
    private static final String REFRESH_TOKEN_URL = "/api/auth/token";

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final TokenExtractor tokenExtractor;
    private final ObjectMapper objectMapper;

    private AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(WebSecurityConfig.AUTHENTICATION_URL, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManagerBean());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
                SIGN_UP_URL,
                AUTHENTICATION_URL,
                REFRESH_TOKEN_URL
        );

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SIGN_UP_URL).permitAll()
                .antMatchers(AUTHENTICATION_URL).permitAll()
                .antMatchers(REFRESH_TOKEN_URL).permitAll()
                .antMatchers(ADMIN_URL).hasAuthority("ADMIN")
                .antMatchers(USER_URL).access("hasAuthority('ROLE_USER')")
                .antMatchers(USER_URL).hasAuthority("ROLE_USER")
                .antMatchers(API_ROOT_URL).authenticated()
                .and()
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/favicon.ico", "/css/**", "/fonts/**", "/images/**", "/js/**");
    }
}