package com.gold.service.interfaces;

import com.gold.dto.Token;
import com.gold.form.LoginForm;
import com.gold.form.RestorePasswordForm;
import com.gold.form.SignUpForm;

public interface AuthenticationService {

    Token login(LoginForm loginForm);

    void signUp(SignUpForm signUpForm);

    void activateUser(String code);

    void logout(String token);

    void restorePassword(RestorePasswordForm passwordForm);

}
