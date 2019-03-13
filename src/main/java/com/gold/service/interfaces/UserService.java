package com.gold.service.interfaces;

import com.gold.dto.User;
import com.gold.form.UserForm;

public interface UserService extends BaseService<User, Long> {

    User findUserByEmail(String email);

    User findByName(String name);

    void signUp(UserForm userForm);
}
