package com.gold.service.impl;

import com.gold.dto.UserDto;
import com.gold.model.User;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private MapperUtils mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperUtils mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapper.convertToDto(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return mapper.convertToDto(user, UserDto.class);
    }

    @Override
    public UserDto findByName(String name) {
        User user = userRepository.findByName(name);
        return mapper.convertToDto(user, UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        User user = getById(id);
        return mapper.convertToDto(user, UserDto.class);
    }

    @Override
    @Transactional
    public void add(UserDto user) {
        User entity = mapper.convertToEntity(user, User.class);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, UserDto user) {
        User entity = getById(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(user, entity);
//        entity.setName(user.getName());
//        entity.setEmail(user.getEmail());
        userRepository.save(entity);
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

//    private UserDto convertToDto(User user) {
//        return mapper.map(user, UserDto.class);
//    }
//
//    private User convertToEntity(UserDto user) {
//        return mapper.map(user, User.class);
//    }
}
