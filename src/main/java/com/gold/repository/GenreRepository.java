package com.gold.repository;

import com.gold.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    GenreEntity findByName(String name);
    @Query("SELECT genre FROM GenreEntity genre LEFT JOIN genre.books as book WHERE book.id=:id")
    GenreEntity findByBookId(@Param("id") Long bookId);
}
