package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class UserDto {

    @JsonView(View.Public.class)
    private String name;

    @JsonView(View.Internal.class)
    private String password;

    @JsonView(View.Public.class)
    private String email;

    @JsonView(View.Internal.class)
    private Set<RoleDto> role;
}
