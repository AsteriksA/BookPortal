package com.gold.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

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
    @Size(min = 6)
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

}