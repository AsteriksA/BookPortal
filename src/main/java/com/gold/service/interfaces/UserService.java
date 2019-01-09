package com.gold.service.interfaces;

import com.gold.model.User;

public interface UserService extends BaseService<User, Long> {

    User findUserByEmail(String email);

    User findByName(String name);

}
