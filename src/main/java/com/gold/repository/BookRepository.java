package com.gold.repository;

import com.gold.model.BookEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByNameLike(String name);

    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a WHERE b.name=:name OR a.firstName=:name OR a.lastName=:name")
    List<BookEntity> findByNameFromSearch(@Param("name") String name);

    List<BookEntity> findByGenre_Name(String genreName);

    List<BookEntity> findByPublisher_Name(String publisherName);

    @EntityGraph(value = "allJoins", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b from BookEntity b WHERE b.id = :id")
    BookEntity findBookByIdWithAllJoins(@Param("id") Long id);

    @EntityGraph(value = "withoutBookContent", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b from BookEntity b WHERE b.id = :id")
    BookEntity findBookByIdWithoutBookContent(@Param("id") Long id);
}
