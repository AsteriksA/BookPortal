package com.gold.repository;

import com.gold.model.BookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookContentRepository extends JpaRepository<BookContent, Long> {
}
