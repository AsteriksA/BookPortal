package com.gold.service.interfaces;

import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends BaseService<User, Long> {

    User findByEmail(String email);
    User findByName(String name);
    User update(Long id, User userForm);
    User changeUserState(Long id, String state);
    User changePassword(Long id, ChangePasswordForm passwordForm);
    void changeName(HttpServletRequest request, Long userId, String name);
}
