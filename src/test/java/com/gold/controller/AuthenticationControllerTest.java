package com.gold.controller;

import com.gold.app.Application;
import com.gold.form.SignUpForm;
import com.gold.repository.UserRepository;
import com.gold.security.service.AuthenticationException;
import com.gold.security.service.JwtAuthenticationRequest;
import com.gold.service.interfaces.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AuthenticationControllerTest {

    private static String NEW_USER = "newUser";

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private  UserRepository userRepository;
    private SignUpForm signUpForm;
    private JwtAuthenticationRequest jwtAuthenticationRequest;

    @Test(expected = AuthenticationException.class)
    public void shouldAuthenticationExceptionWithIncorrectPassword() {
        jwtAuthenticationRequest = new JwtAuthenticationRequest("admin", "aadmin");
        authenticationService.createAuthenticationToken(jwtAuthenticationRequest);
    }

    @Test(expected = AuthenticationException.class)
    public void shouldAuthenticationExceptionWithNotExistLogin() {
        jwtAuthenticationRequest = new JwtAuthenticationRequest("fail", "admin");
        authenticationService.createAuthenticationToken(jwtAuthenticationRequest);
    }

    @Test
    public void successfulTokenWithCorrectLoginAndPassword() {
        jwtAuthenticationRequest = new JwtAuthenticationRequest("admin", "admin");
        authenticationService.createAuthenticationToken(jwtAuthenticationRequest);
    }

    @Test(expected = EntityExistsException.class)
    public void shouldEntityExistsExceptionWithExistUsernameAndEmail() {
        signUpForm = new SignUpForm("admin", "admin", "teltegekni@desoz.com");
        authenticationService.signUp(signUpForm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldEntityExistsExceptionWithExistUsername() {
        signUpForm = new SignUpForm("admin", "admin", "yandex@pot.com");
        authenticationService.signUp(signUpForm);
    }

    @Test(expected = EntityExistsException.class)
    public void shouldEntityExistsExceptionWithExistEmail() {
        signUpForm = new SignUpForm("peter", "admin", "teltegekni@desoz.com");
        authenticationService.signUp(signUpForm);
    }

    @Test
    public void successfulSaveNewUser() {
        signUpForm = new SignUpForm("newUser", "password", "test@tt.com");
        authenticationService.signUp(signUpForm);
        userRepository.delete(userRepository.findByUsername(NEW_USER).
                orElseThrow(() -> new EntityNotFoundException("User with this email:" + NEW_USER + " is exist")));
    }
}
