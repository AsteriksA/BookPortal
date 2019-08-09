package com.gold.controller;

import com.gold.config.WebSecurityConfig;
import com.gold.dto.User;
import com.gold.form.SignUpForm;
import com.gold.security.service.JwtAuthenticationRequest;
import com.gold.security.service.JwtAuthenticationResponse;
import com.gold.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.gold.config.WebSecurityConfig.API_URL;

@RestController
@RequestMapping(API_URL +"/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign_up")
    public void signUp(@RequestBody SignUpForm signUpForm) {
        authenticationService.signUp(signUpForm);
    }

    @GetMapping("/activate/{registration_code}")
    public void activateUserByRegistrationCode(@PathVariable(name = "registration_code") String code) {
        authenticationService.activateUser(code);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }

    @PostMapping("/restore_password")
    public void restorePassword(@RequestBody User passwordForm) {
        authenticationService.restorePassword(passwordForm);
    }

    @GetMapping("/refresh_token")
    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String tokenPayload = request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
        return authenticationService.refreshToken(tokenPayload);
    }

//    TODO: implement this method
    @GetMapping("/logout")
    public void logout() {
      throw  new UnsupportedOperationException();
    }

}
