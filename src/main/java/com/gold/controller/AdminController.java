package com.gold.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.dto.User;
import com.gold.form.ChangePasswordForm;
import com.gold.form.UpdateUserForm;
import com.gold.repository.UserRepository;
import com.gold.service.interfaces.UserService;
import com.gold.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final UserService userService;

    @JsonView(View.Internal.class)
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUserById(@PathVariable Long id) {
        userService.remove(id);
    }

    @PostMapping("/users/{id}")
    public void bannedUser(@PathVariable Long id, @RequestParam(name = "isBanned") Boolean isBanned) {
        userService.bannedById(id);
    }

    @PutMapping("/users/{id}/change")
    public ResponseEntity<Object> change(@PathVariable Long id, @RequestBody UpdateUserForm userForm) {
        userService.update(id, userForm);
        return ResponseEntity.ok().build();
    }

}
