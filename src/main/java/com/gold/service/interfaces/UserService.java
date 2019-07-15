package com.gold.service.interfaces;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;

public interface UserService extends BaseService<User, Long> {

    User findByEmail(String email);
    User findByName(String name);
    User update(Long id, User userForm);
    User bannedById(Long id, Boolean isBan);
    User changePassword(Long id, ChangePasswordForm passwordForm);
}
