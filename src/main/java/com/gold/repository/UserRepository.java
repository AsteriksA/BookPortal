package com.gold.repository;

import com.gold.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByActivationCode(String code);
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findById(Long id);

}
