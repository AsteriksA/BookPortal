package com.gold.service.impl;

import com.gold.model.User;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addEntity(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeEntity(Long id) {
        userRepository.deleteById(id);
    }

//    TODO
    @Override
    @Transactional
    public void updateEntity(Long id, User user) {

        User olduser = findById(id);
        if (olduser != null) {

        }
        throw new UnsupportedOperationException();
    }
}
