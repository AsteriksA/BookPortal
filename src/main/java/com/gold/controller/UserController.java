package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.form.UpdateUserForm;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
    @GetMapping(value = "/id/{userId}")
    public User getUser(@NotNull @PathVariable(value = "userId") Long userId) {
        return userService.findOne(userId);
    }

    @JsonView(View.Internal.class)
    @GetMapping(value = "/name/{name}")
    public User getUser(@PathVariable(value = "name") String name) {
        return userService.findByName(name);
    }

//    @PostMapping("/add")
//    public ResponseEntity<Object> add(@RequestBody SignUpForm userForm) {
//        userService.signUp(userForm);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserForm userForm) {
        userService.update(id, userForm);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/changePassword")
    public ResponseEntity<Object> changePassword(@PathVariable Long id, @RequestBody ChangePasswordForm passwordForm) {
        userService.changePassword(id, passwordForm);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok().build();
    }

}
