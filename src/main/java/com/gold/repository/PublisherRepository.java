package com.gold.repository;

import com.gold.model.Book;
import com.gold.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Publisher findByName(String name);

    @Query("SELECT p.books FROM Publisher p JOIN FETCH p.books WHERE p.id=:id")
    List<Book> getAllBooks(Long id);

}
