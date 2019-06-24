package com.gold.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@NotBlank(message = "Login or Password not provided")
public class LoginForm {

    private String username;
    private String password;

    @JsonCreator
    public LoginForm(@JsonProperty("name") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
