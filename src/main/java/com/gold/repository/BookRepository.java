package com.gold.repository;

import com.gold.model.Book;
import com.gold.model.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameLike(String name);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE b.name=:name OR a.firstName=:name OR a.lastName=:name")
    List<Book> findByNameFromSearch(@Param("name") String name);

    List<Book> findByGenre_Name(String genreName);

    List<Book> findByPublisher_Name(String publisherName);

    @EntityGraph(value = "allJoins", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b from Book b WHERE b.id = :id")
    Book findBookByIdWithAllJoins(@Param("id") Long id);

    @EntityGraph(value = "withoutBookContent", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b from Book b WHERE b.id = :id")
    Book findBookByIdWithoutBookContent(@Param("id") Long id);
}
