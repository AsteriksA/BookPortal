package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.UserDto;
import com.gold.model.User;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(View.Internal.class)
    @GetMapping(value = "/{name}")
    public UserDto getUser(@PathVariable(value = "name") String name) {
        return userService.findByName(name);
    }
}
