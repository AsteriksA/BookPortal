package com.gold.model;

import com.gold.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
//@Data
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

//    must be hashpassword
    @NotNull
    @Size(min = 5)
    private String password;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String activationCode;

    @ElementCollection(targetClass = RoleEntity.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleEntity> roles;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    Set<TokenEntity> tokens;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .activationCode(UUID.randomUUID().toString())
                .roles(Collections.singleton(RoleEntity.ROLE_USER))
                .build();
    }

}