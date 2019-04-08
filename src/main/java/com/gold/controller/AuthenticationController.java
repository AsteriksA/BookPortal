package com.gold.controller;

import com.gold.dto.Token;
import com.gold.form.RestorePasswordForm;
import com.gold.form.LoginForm;
import com.gold.form.SignUpForm;
import com.gold.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpForm signUpForm) {
        authenticationService.signUp(signUpForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/sign_up")
    public ResponseEntity<Object> signUpFail() {
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/login?activationCode={code}")
    @GetMapping("/login/activate/{code}")
    public ResponseEntity<Object> activate(@PathVariable String code) {
        authenticationService.activateUser(code);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/api/login")
    public ResponseEntity<Token> login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(authenticationService.login(loginForm));
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam(required = false) String logout,
                                        @RequestHeader("Lock-Token") String token) {
        if (logout != null) {
            authenticationService.logout(token);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("restorepsw")
    public void restorePassword(@RequestBody RestorePasswordForm passwordForm) {
        authenticationService.restorePassword(passwordForm);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Lock-Token") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }

}
