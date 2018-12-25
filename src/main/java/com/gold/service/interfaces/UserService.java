package com.gold.service.interfaces;

import com.gold.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getUserByEmail(String email);

    User getUserByName(String name);

    User getUserById(Long Id);

    void addUser(User user);

    void removeUser(Long id);

    void updateUser(Long id);

}
