package com.gold.service.impl;

import com.gold.dto.Token;
import com.gold.form.LoginForm;
import com.gold.form.RestorePasswordForm;
import com.gold.form.SignUpForm;
import com.gold.model.RoleEntity;
import com.gold.model.State;
import com.gold.model.TokenEntity;
import com.gold.model.UserEntity;
import com.gold.repository.TokenRepository;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServiceImpl implements AuthenticationService {

    private final static String RESTORE_MESSAGE = "Hello, %s%n!" +
            "Your new password: %s%n Please, visit next link: http://localhost:8080/login";
    private final static String MESSAGE = "Hello, %s! %n" +
            "Welcome to BookPortal. Please, visit next link: http://localhost:8080/login/activate/%s";
    private final static String SUBJECT_ACTIVATE = "Activation code";
    private final static String SUBJECT_RESTORE_PASSWORD = "Restore password";
    private final static Integer DEFAULT_LENGTH_PASSWORD =10;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public Token login(LoginForm loginForm) {
        Optional<UserEntity> userCandidate = userRepository.findByName(loginForm.getName());

        if (userCandidate.isPresent()) {
            UserEntity entity = userCandidate.get();

            if (entity.getState() != null && passwordEncoder.matches(loginForm.getPassword(), entity.getPassword())) {
                TokenEntity token = TokenEntity.builder()
                        .user(entity)
                        .value(UUID.randomUUID().toString())
                        .build();

                tokenRepository.save(token);
                return Token.from(token);
            }
        }
        String exceptionMessage = String
                .format("User not found with login: %s, password: %s", loginForm.getName(), loginForm.getPassword());
        throw new EntityNotFoundException(exceptionMessage);
    }

    @Override
    public void logout(String token) {
        Optional<TokenEntity> tokenCandidate = tokenRepository.findOneByValue(token);

        if (!tokenCandidate.isPresent()) {
            throw new EntityNotFoundException("Token isn't exist!");
        }
        tokenRepository.delete(tokenCandidate.get());
    }

    @Override
    public void signUp(SignUpForm signUpForm) {
        Optional<UserEntity> userCandidate = userRepository.findByName(signUpForm.getName());

        if (userCandidate.isPresent()) {
            throw new EntityExistsException("An account is already existed");
        }
        String hashPassword = passwordEncoder.encode(signUpForm.getPassword());

        UserEntity entity = build(signUpForm, hashPassword);
        userRepository.save(entity);

        String message = String.format(MESSAGE, entity.getName(), entity.getActivationCode());
        mailService.send(entity.getEmail(), SUBJECT_ACTIVATE, message);
    }

    @Override
    @Transactional
    public void restorePassword(RestorePasswordForm passwordForm) {
        UserEntity entity = userRepository.findByEmail(passwordForm.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User with this email: " + passwordForm.getEmail() +" doesn't exist"));

        String password = RandomStringUtils.random(DEFAULT_LENGTH_PASSWORD, true, true);
        String hashPassword = passwordEncoder.encode(password);
        entity.setPassword(hashPassword);

        String message = String.format(RESTORE_MESSAGE, entity.getName(), password);
        mailService.send(passwordForm.getEmail(), SUBJECT_RESTORE_PASSWORD, message);
    }

    private UserEntity build(SignUpForm signUpForm, String hashPassword) {
        return UserEntity.builder()
                .name(signUpForm.getName())
                .password(hashPassword)
                .email(signUpForm.getEmail())
                .activationCode(UUID.randomUUID().toString())
                .roles(Collections.singleton(RoleEntity.ROLE_USER))
                .build();
    }

    @Override
    public void activateUser(String code) {
        Optional<UserEntity> userCandidate = userRepository.findByActivationCode(code);

        if (!userCandidate.isPresent()) {
            throw new UsernameNotFoundException("User with this activation code doesn't exist!");
        }

        UserEntity entity = userCandidate.get();
        entity.setState(State.ACTIVATED);
        entity.setActivationCode(null);

        userRepository.save(entity);
    }
}
