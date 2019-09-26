package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.gold.config.WebSecurityConfig.API_URL;

@RestController
@RequestMapping(API_URL + "/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

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

    @JsonView(View.Public.class)
    @PutMapping("/{userId}")
    @PreAuthorize("@restAccessService.isExistedUser(#userId, authentication)")
    public User update(@PathVariable Long userId,
                       @RequestBody User userForm) {
        return userService.update(userId, userForm);
    }

    @PutMapping("/{userId}/name")
    @PreAuthorize("@restAccessService.isExistedUser(#userId, authentication)")
    public void changeUserName(HttpServletRequest request,
                               @PathVariable Long userId,
                               @RequestParam String name) {
        userService.changeName(request, userId, name);
    }

    @PutMapping("/{userId}/changePassword")
    public User changePassword(@PathVariable Long userId,
                               @RequestBody ChangePasswordForm passwordForm) {
        return userService.changePassword(userId, passwordForm);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@restAccessService.isExistedUser(#userId, authentication)")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
