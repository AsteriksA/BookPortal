package com.gold.controller;

import com.gold.dto.Token;
import com.gold.form.LoginForm;
import com.gold.form.UserForm;
import com.gold.service.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final LoginService service;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }



//    @PostMapping()
//    public String signUp(UserForm userForm) {
//        service.login(userForm);
//        return "redirect:/sign_up";
//    }
//
//    @GetMapping
//    public String getLoginPage(Authentication authentication, ModelMap model, HttpServletRequest request) {
//        if (authentication != null) {
//            return "redirect:/";
//        }
//        if (request.getParameterMap().containsKey("error")) {
//            model.addAttribute("error", true);
//        }
//        return "login";
//    }


}
