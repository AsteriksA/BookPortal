package com.gold.config;

import com.gold.handler.CustomLogoutSuccessHandler;
import com.gold.security1.filter.TokenAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
//@ComponentScan("com.gold.security1")
//@RequiredArgsConstructor(onConstructor_= {@Autowired})
public class WebSecurityConfigDeprecated extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final AuthenticationProvider authenticationProvider;
    private final TokenAuthFilter tokenAuthFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final ResponseEntityExceptionHandler exceptionHandler;

    public WebSecurityConfigDeprecated(UserDetailsService userDetailsService, AuthenticationProvider authenticationProvider, TokenAuthFilter tokenAuthFilter, AuthenticationEntryPoint authenticationEntryPoint, ResponseEntityExceptionHandler exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
        this.tokenAuthFilter = tokenAuthFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.exceptionHandler = exceptionHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
//                .addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .antMatcher("/**")
                .authenticationProvider(authenticationProvider)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                    .antMatchers("/users/**").hasAuthority("ADMIN")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/profile/**").hasAuthority("ADMIN")
                    .antMatchers("/books/**").permitAll()
                    .antMatchers("/login/*").permitAll()
                    .antMatchers("/sign_up/*").permitAll()
                    .antMatchers("/logout").permitAll()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .logoutSuccessUrl("/login")
                    .and()
                .formLogin().disable();
//                    .loginPage("/api/login")
////                    .loginProcessingUrl("/api/login")
//                    .defaultSuccessUrl("/users")
//                    .failureForwardUrl("/api/sign_up")
//                    .usernameParameter("name")
//                    .passwordParameter("password")
//                    .permitAll();
//                    .successHandler(authSuccessHandler)
//                    .failureHandler(authFailureHandler)
//                    .and()
//                .logout()
//                    .logoutUrl("logout")
//                    .logoutSuccessUrl("/bookPortal/login");
//                    .and()
//                .rememberMe()
//                    .tokenRepository()
//                    .rememberMeParameter()
//                    .tokenValiditySeconds(86400);

        http.csrf().disable();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/", "/sign_up/**").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .usernameParameter("name")
//                    .defaultSuccessUrl("/")
//                    .loginPage("/registration")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll()
//                    .and()
//                .rememberMe()
//                    .rememberMeParameter("remember-me")
//                    .tokenRepository(tokenRepository());
//    }
//
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
//
//    @Bean
//    public PersistentTokenRepository tokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        return  tokenRepository;
//    }


}
