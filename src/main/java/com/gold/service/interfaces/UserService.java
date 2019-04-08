package com.gold.service.interfaces;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.form.UpdateUserForm;

public interface UserService extends BaseService<User, Long> {

    User findByEmail(String email);

    User findByName(String name);

//    void signUp(SignUpForm userForm);

    void update(Long id, UpdateUserForm userForm);

    void bannedById(Long id);

    void changePassword(Long id, ChangePasswordForm passwordForm);

}
