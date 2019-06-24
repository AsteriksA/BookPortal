package com.gold.service.interfaces;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.form.UpdateUserForm;

public interface UserService extends BaseService<User, Long> {

    User findByEmail(String email);

    User findByName(String name);

//    void signUp(SignUpForm userForm);

    User update(Long id, UpdateUserForm userForm);

    User bannedById(Long id);

    User changePassword(Long id, ChangePasswordForm passwordForm);

}
