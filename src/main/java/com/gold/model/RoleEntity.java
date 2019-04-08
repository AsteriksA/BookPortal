package com.gold.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;


public enum RoleEntity implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ANONYMOUS, ROLE_MODERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}


//@Entity
//@Getter
//@Setter
//@Table(name = "roleEntities")
//public class RoleEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String role;
//
////    for example: ADMIN, USER, ANONYMOUS, MODERATOR
//    @ManyToMany(mappedBy = "roleEntities")
//    private Set<UserEntity> userEntities;
//
//}
