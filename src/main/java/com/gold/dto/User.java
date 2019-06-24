package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.RoleEntity;
import com.gold.model.UserEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.Public.class)
public class User {

    @JsonView(View.Internal.class)
    private Long id;
    private String username;

    private String email;

    @JsonView(View.Internal.class)
    private Set<RoleEntity> roles;

    public static User from(UserEntity entity) {
        return User.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }

    public static List<User> from(List<UserEntity> entities) {
        return entities.stream()
                .map(User::from)
                .collect(Collectors.toList());
    }
}
