package com.gold.service.impl;

import com.gold.dto.User;
import com.gold.form.SignUpForm;
import com.gold.model.RoleEntity;
import com.gold.model.State;
import com.gold.model.UserEntity;
import com.gold.repository.UserRepository;
import com.gold.security2.jwt.JwtTokenUtil;
import com.gold.security2.jwt.JwtUser;
import com.gold.security2.service.AuthenticationException;
import com.gold.security2.service.JwtAuthenticationRequest;
import com.gold.security2.service.JwtAuthenticationResponse;
import com.gold.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void signUp(SignUpForm signUpForm) {
        Optional<UserEntity> userCandidate = userRepository.findByEmail(signUpForm.getEmail());
        if (userCandidate.isPresent()) {
            throw new EntityExistsException("An account is already existed");
        }
        String hashPassword = passwordEncoder.encode(signUpForm.getPassword());

        UserEntity entity = build(signUpForm, hashPassword);
        userRepository.save(entity);

        String message = String.format(MESSAGE, entity.getUsername(), entity.getActivationCode());
        mailService.send(entity.getEmail(), SUBJECT_ACTIVATE, message);
        System.out.println("success");
    }

    @Override
    @Transactional
    public void restorePassword(User passwordForm) {
        UserEntity entity = userRepository.findByEmail(passwordForm.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User with this email: " + passwordForm.getEmail() +" doesn't exist"));

        String password = RandomStringUtils.random(DEFAULT_LENGTH_PASSWORD, true, true);
        String hashPassword = passwordEncoder.encode(password);
        entity.setPassword(hashPassword);

        String message = String.format(RESTORE_MESSAGE, entity.getUsername(), password);
        mailService.send(passwordForm.getEmail(), SUBJECT_RESTORE_PASSWORD, message);
    }

    private UserEntity build(SignUpForm signUpForm, String hashPassword) {
        return UserEntity.builder()
                .username(signUpForm.getUsername())
                .password(hashPassword)
                .email(signUpForm.getEmail())
                .activationCode(UUID.randomUUID().toString())
                .state(State.REGISTERED)
                .lastPasswordResetDate(new Date(System.currentTimeMillis()))
                .roles(Collections.singleton(RoleEntity.ROLE_USER))
                .build();
    }

    @Override
    public void activateUser(String code) {
        UserEntity userCandidate =userRepository.findByActivationCode(code)
                .orElseThrow(()-> new UsernameNotFoundException("User with this activation code doesn't exist!"));

        userCandidate.setState(State.ACTIVATED);
        userCandidate.setActivationCode(null);
        userRepository.save(userCandidate);
    }

//    TODO: invoke two times loadByUsername() in this method
    @Override
    public ResponseEntity<?> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(tokenMap));
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(String tokenPayload) {
        String token = tokenPayload.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        Map<String, String> tokenMap = new HashMap<>();
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getEntity().getLastPasswordResetDate() )) {
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            String refreshToken = jwtTokenUtil.generateRefreshToken(user);
            tokenMap.put("accessToken", accessToken);
            tokenMap.put("refreshToken", refreshToken);
            return ResponseEntity.ok(new JwtAuthenticationResponse(tokenMap));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
