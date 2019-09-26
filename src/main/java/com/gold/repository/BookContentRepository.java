package com.gold.repository;

import com.gold.model.BookContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookContentRepository extends JpaRepository<BookContentEntity, Long> {
}
