package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.RoleEntity;
import com.gold.model.UserEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonView(View.Public.class)
    private String name;

    @JsonView(View.Internal.class)
    private String password;

    @JsonView(View.Public.class)
    private String email;

    @JsonView(View.Internal.class)
    private Set<RoleEntity> roles;

    public static User from(UserEntity entity) {
        return User.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    public static List<User> from(List<UserEntity> entities) {
        return entities.stream()
                .map(User::from)
                .collect(Collectors.toList());
    }
}
