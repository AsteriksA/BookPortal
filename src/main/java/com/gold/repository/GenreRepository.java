package com.gold.repository;

import com.gold.model.Book;
import com.gold.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);

//    @Query("SELECT g.books FROM Genre g JOIN FETCH g.books WHERE g.id=:id")
//    List<Book> findAllBooks(Long id);
}
