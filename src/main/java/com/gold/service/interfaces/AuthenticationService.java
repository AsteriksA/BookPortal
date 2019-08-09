package com.gold.service.interfaces;

import com.gold.dto.User;
import com.gold.form.SignUpForm;
import com.gold.security.service.AuthenticationException;
import com.gold.security.service.JwtAuthenticationRequest;
import com.gold.security.service.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    void signUp(SignUpForm signUpForm);
    void activateUser(String code);
    void restorePassword(User passwordForm);

    ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException;
    ResponseEntity<JwtAuthenticationResponse> refreshToken(String tokenPayload);
}
