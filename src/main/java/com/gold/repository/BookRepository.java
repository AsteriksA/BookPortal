package com.gold.repository;

import com.gold.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameLike(String name);

    List<Book> findByAuthors(String author);

    List<Book> findByGenre(String genre);

    @Query("SELECT b FROM Book b JOIN FETCH b.content WHERE b.id = (:id)")
    Book findByIdAndFetchContentEagerly(@Param("id") Long id);




}
