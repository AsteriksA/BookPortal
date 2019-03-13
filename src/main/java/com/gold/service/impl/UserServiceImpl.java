package com.gold.service.impl;

import com.gold.dto.User;
import com.gold.form.UserForm;
import com.gold.model.RoleEntity;
import com.gold.model.State;
import com.gold.model.UserEntity;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import com.gold.util.EntityUtils;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final EntityMapper mapper;

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return mapper.convertToDto(userEntities, User.class);
    }

    @Override
    public User findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return mapper.convertToDto(userEntity, User.class);
    }

    @Override
    public User findByName(String name) {
        Optional<UserEntity> userCandidate = userRepository.findByName(name);
        if (userCandidate.isPresent()) {
            UserEntity entity = userCandidate.get();
            return User.from(entity);
        }
        throw new IllegalArgumentException("User not found");
//        UserEntity userEntity = userRepository.findByName(name);
//        return mapper.convertToDto(userEntity, User.class);
    }

    @Override
    public User findOne(Long id) {
        UserEntity userEntity = getById(id);
        return mapper.convertToDto(userEntity, User.class);
    }

    @Override
    @Transactional
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        sendSimpleEmail();

        UserEntity entity = UserEntity.builder()
                .name(userForm.getName())
                .password(hashPassword)
                .email(userForm.getEmail())
                .state(State.ACTIVATED)
                .roles(Collections.singleton(RoleEntity.USER))
                .build();

        userRepository.save(entity);
    }

    private void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("asterstar@i.ua");
        message.setSubject("test simple email");
        message.setText("Welcome! You are successfully registred");
        mailSender.send(message);
    }

    @Override
    @Transactional
    public void add(User user) {
        UserEntity entity = mapper.convertToEntity(user, UserEntity.class);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, User user) {
        UserEntity entity = getById(id);
        EntityUtils.isNull(entity);
        mapper.convertToEntity(user, entity);
        userRepository.save(entity);
    }

    private UserEntity getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
