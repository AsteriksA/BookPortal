package com.gold.service.impl;

import com.gold.app.Application;
import com.gold.repository.UserRepository;
import com.gold.security2.jwt.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AuthenticationServiceImplTest {

    @MockBean
    private  UserRepository userRepository;
    @MockBean
    private MailService mailService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Before
    public void setUp() {
    }

    @Test
    public void tt() {
        assertEquals(2,3);
    }
}