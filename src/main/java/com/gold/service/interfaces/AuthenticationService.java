package com.gold.service.interfaces;

import com.gold.form.RestorePasswordForm;
import com.gold.form.SignUpForm;
import com.gold.security2.service.AuthenticationException;
import com.gold.security2.service.JwtAuthenticationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    void signUp(SignUpForm signUpForm);
    void activateUser(String code);
    void restorePassword(RestorePasswordForm passwordForm);

    ResponseEntity<?> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException;

    ResponseEntity<?> refreshToken(String tokenPayload);
}
