package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.User;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gold.config.WebSecurityConfig.API_URL;

@RestController
@RequestMapping(API_URL + "/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final UserService userService;

    @JsonView(View.Internal.class)
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @PutMapping("/users/{userId}")
    public User banUserById(@PathVariable Long userId, @RequestParam(name="isBanned") Boolean isBanned) {
        return userService.banById(userId, isBanned);
    }
}
