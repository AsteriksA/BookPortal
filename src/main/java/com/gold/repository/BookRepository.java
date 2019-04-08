package com.gold.repository;

import com.gold.model.BookEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByNameLike(String name);

//    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a " +
//            "WHERE b.name=:name OR a.firstName=:name OR a.lastName=:name")
//    List<BookEntity> findByNameFromSearch(@Param("name") String name);

//    @Query("SELECT b FROM BookEntity b JOIN b.authors a " +
//            "WHERE b.name MEMBER OF paremetres OR a.firstName " +
//            "member of  parametres or a.lastName member of parametres" )
    @Query("SELECT b FROM BookEntity b LEFT JOIN b.authors aut " +
            "WHERE b.name IN :parameters OR aut.firstName IN :parameters " +
            "OR aut.lastName IN :parameters")
    List<BookEntity> findBySearch(@Param("parameters") String... parameters);

    List<BookEntity> findByGenre_Name(String genreName);

    List<BookEntity> findByPublisher_Name(String publisherName);

    @EntityGraph(value = "allJoins", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b from BookEntity b WHERE b.id = :id")
    BookEntity findBookByIdWithAllJoins(@Param("id") Long id);

    @EntityGraph(value = "withoutBookContent", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b from BookEntity b WHERE b.id = :id")
    BookEntity findBookByIdWithoutBookContent(@Param("id") Long id);

    void deleteById(Long id);

    Optional<BookEntity> findById(Long id);

}
