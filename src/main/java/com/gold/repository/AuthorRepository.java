package com.gold.repository;

import com.gold.model.Author;
import com.gold.model.Book;
import com.gold.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFirstNameLikeOrSecondNameLike(String firstName, String secondName);

    @Query("SELECT a.books FROM Author a JOIN FETCH a.books WHERE a.id= :id")
    List<Book> getAllBooks(Long id);

}
