package com.gold.repository;

import com.gold.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface RoleRepository /*extends JpaRepository<RoleEntity, Long> */{

    RoleEntity findByName(String name);
}
