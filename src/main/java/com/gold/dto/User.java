package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.RoleEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
}
