package com.gold.config;

import com.gold.security2.jwt.JwtAuthenticationEntryPoint;
import com.gold.security2.jwt.JwtAuthorizationTokenFilter;
import com.gold.security2.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig2 extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String API_URL = "/api";
    private static final String API_ROOT_URL = "/api/**";
    private static final String ADMIN_URL = "/api/admin/*";
    private static final String OWNER_URL = "/api/owner/*";
    private static final String HEAD_URL = "/api/department/*";
    private static final String ACTIVATE_URL = "/api/auth/activate/*";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String SIGN_UP_URL = "/api/auth/signup";
    private static final String REFRESH_TOKEN_URL = "/api/auth/refresh";

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
                .antMatchers(LOGIN_URL).permitAll()
                .antMatchers(ACTIVATE_URL).permitAll()
                .antMatchers(REFRESH_TOKEN_URL).permitAll()
                .antMatchers(ADMIN_URL).hasAuthority("ADMIN")
                .antMatchers(OWNER_URL).hasAuthority("OWNER")
                .antMatchers(HEAD_URL).hasAuthority("HEAD")
                .antMatchers(API_ROOT_URL).authenticated()
                .and()
                .addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web){
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        "/api/auth/*"
                )
                .and()
                .ignoring().antMatchers("/", "/favicon.ico", "/css/**", "/fonts/**", "/images/**", "/js/**");
    }
}