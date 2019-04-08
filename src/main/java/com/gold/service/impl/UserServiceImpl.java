package com.gold.service.impl;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.form.UpdateUserForm;
import com.gold.model.State;
import com.gold.model.UserEntity;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import com.gold.util.EntityUtils;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        log.info("Get all users");
        return User.from(userEntities);
//        return mapper.convertToDto(userEntities, User.class);
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> userCandidate = userRepository.findByEmail(email);
        return getUser(userCandidate);
//        return mapper.convertToDto(userEntity, User.class);
    }

    @Override
    public User findByName(String name) {
        Optional<UserEntity> userCandidate = userRepository.findByName(name);

        return getUser(userCandidate);
    }

    @Override
    public User findOne(Long id) {
        UserEntity userEntity = getById(id);
        return mapper.convertToDto(userEntity, User.class);
    }

//    TODO: Is this method necessary?
    @Override
    @Transactional
    public void add(User user) {
        UserEntity entity = mapper.convertToEntity(user, UserEntity.class);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userRepository.delete(getById(id));
    }

    @Override
    @Transactional
    public void update(Long id, UpdateUserForm user) {
        UserEntity entity = getById(id);
        mapper.convertToEntity(user, entity);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordForm passwordForm) {
        UserEntity entity = getById(id);
        checkPassword(entity.getPassword(), passwordForm.getPassword());
        String hashPassword = passwordEncoder.encode(passwordForm.getNewPassword());
        entity.setPassword(hashPassword);
        userRepository.save(entity);
    }

    private void checkPassword(String passwordForm, String entityPassword) {
        if (!passwordEncoder.matches(passwordForm, entityPassword)) {
            throw new IllegalArgumentException("The password doesn't match");
        }
    }

    @Override
    @Transactional
    public void bannedById(Long id) {
        UserEntity entity = getById(id);
        entity.setState(State.BANNED);
        userRepository.save(entity);
    }

    private User getUser(Optional<UserEntity> userCandidate) {
        if (userCandidate.isPresent()) {
            UserEntity entity = userCandidate.get();
            return User.from(entity);
        }
        throw new UsernameNotFoundException("User not found");
    }

    private UserEntity getById(Long id) {
        UserEntity entity = userRepository.findById(id).orElse(null);
        EntityUtils.isNull(entity);
        return entity;
    }
}
