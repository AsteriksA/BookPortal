package com.gold.controller;

import com.gold.config.WebSecurityConfig2;
import com.gold.form.RestorePasswordForm;
import com.gold.form.SignUpForm;
import com.gold.security2.service.JwtAuthenticationRequest;
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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public void signUp(@RequestBody SignUpForm signUpForm) {
        authenticationService.signUp(signUpForm);
    }

    @GetMapping("activate/{code}")
    public void activate(@PathVariable String code) {
        authenticationService.activateUser(code);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }

    @PostMapping("forgotPassword")
    public void restorePassword(@RequestBody RestorePasswordForm passwordForm) {
        authenticationService.restorePassword(passwordForm);
    }

    @GetMapping("refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String tokenPayload = request.getHeader(WebSecurityConfig2.AUTHENTICATION_HEADER_NAME);
        return authenticationService.refreshToken(tokenPayload);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<Object> logout(@RequestHeader("Lock-Token") String token) {
//        authenticationService.logout(token);
//        return ResponseEntity.ok().build();
//    }

}
