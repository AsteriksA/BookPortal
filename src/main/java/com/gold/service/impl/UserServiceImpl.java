package com.gold.service.impl;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.model.State;
import com.gold.model.UserEntity;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import com.gold.util.EntityUtils;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;

    @Override
    public List<User> findAll() {
        return mapper.convertToDto(userRepository.findAll(), User.class);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userCandidate = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with this email:" + email + " is exist"));
        return mapper.convertToDto(userCandidate, User.class);
    }

    @Override
    public User findByName(String name) {
        UserEntity userCandidate = userRepository.findByUsername(name)
                .orElseThrow(() -> new EntityNotFoundException("User with this email:" + name + " is exist"));
        return mapper.convertToDto(userCandidate, User.class);
    }

    @Override
    public User findOne(Long id) {
        return mapper.convertToDto(getById(id), User.class);
    }

    @Override
    public User add(User user) {
        throw new UnsupportedOperationException("For adding user use the sign_up procedure");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User update(Long id, User userForm) {
        UserEntity entity = getById(id);
        entity.setUsername(userForm.getUsername());
        entity.setEmail(userForm.getEmail());
        userRepository.save(entity);
        return mapper.convertToDto(entity, User.class);
    }

    @Override
    @Transactional
    public User changePassword(Long id, ChangePasswordForm passwordForm) {
        UserEntity user = getById(id);
        checkPassword(passwordForm.getPassword(), user.getPassword());
        user.setPassword(passwordEncoder.encode(passwordForm.getNewPassword()));
        userRepository.save(user);
        return mapper.convertToDto(user, User.class);
    }

    private void checkPassword(String passwordForm, String entityPassword) {
        if (!passwordEncoder.matches(passwordForm, entityPassword)) {
            throw new IllegalArgumentException("The password doesn't match");
        }
    }

    @Override
    @Transactional
    public User banById(Long id, Boolean isBanned) {
        if (!isBanned){
            return mapper.convertToDto(this.getById(id), User.class);
        }
        UserEntity entity = getById(id);
        entity.setState(State.BANNED);
        return mapper.convertToDto(userRepository.save(entity), User.class);
    }

    private UserEntity getById(Long id) {
        UserEntity entity = userRepository.findById(id).orElse(null);
        EntityUtils.notNull(entity);
        return entity;
    }
}
