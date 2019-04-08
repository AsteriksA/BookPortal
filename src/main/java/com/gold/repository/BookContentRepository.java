package com.gold.repository;

import com.gold.model.BookContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookContentRepository extends JpaRepository<BookContentEntity, Long> {

    Optional<BookContentEntity> findById(Long id);
}
