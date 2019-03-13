package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.User;
import com.gold.form.UserForm;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(View.Internal.class)
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @JsonView(View.Internal.class)
    @GetMapping(value = "/{user-id}")
    public User getUser(@PathVariable(value = "user-id") Long userId) {
        return userService.findOne(userId);
    }

//    @JsonView(View.Internal.class)
//    @GetMapping(value = "/{name}")
    public User getUser(@PathVariable(value = "name") String name) {
        return userService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserForm userForm) {
        userService.signUp(userForm);
        return ResponseEntity.ok().build();
    }
}
