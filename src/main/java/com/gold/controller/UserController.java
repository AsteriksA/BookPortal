package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.security2.jwt.JwtUser;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gold.config.WebSecurityConfig2.API_URL;

@RestController
@RequestMapping(API_URL + "/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(View.Internal.class)
    @GetMapping(value = "/{userId}")
    public User getUserById(@PathVariable(value = "userId") Long userId) {
        return userService.findOne(userId);
    }

    @JsonView(View.Internal.class)
    @GetMapping
    public User getUserByParam(@RequestParam(name = "name", required = false) String userName) {
        return userService.findByName(userName);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody User userForm) {
        verifyUser(userId);
        return userService.update(userId, userForm);
    }

    @PutMapping("/{userId}/changePassword")
    public User changePassword(@PathVariable Long userId, @RequestBody ChangePasswordForm passwordForm) {
        verifyUser(userId);
        return userService.changePassword(userId, passwordForm);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        verifyUser(userId);
        userService.delete(userId);
    }

    private void verifyUser(Long userId) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!jwtUser.getEntity().getId().equals(userId)) {
            throw new AccessDeniedException("You cannot change another account");
        }
    }
}
