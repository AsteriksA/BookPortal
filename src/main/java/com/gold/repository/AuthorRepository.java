package com.gold.repository;

import com.gold.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFirstNameOrSecondName(String firstName, String secondName);

//    @Query("SELECT a.books FROM Author a JOIN FETCH a.books WHERE a.id= :id")
//    List<Book> findAllBooks(Long id);

}
