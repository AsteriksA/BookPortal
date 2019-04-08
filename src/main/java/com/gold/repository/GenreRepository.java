package com.gold.repository;

import com.gold.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    GenreEntity findByName(String name);

    void deleteById(Long id);

    Optional<GenreEntity> findById(Long id);

}
