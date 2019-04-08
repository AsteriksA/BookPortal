package com.gold.repository;

import com.gold.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    List<AuthorEntity> findByFirstNameOrLastName(String firstName, String secondName);

    AuthorEntity findByFirstNameAndLastName(String firstName, String secondName);

    void deleteById(Long id);

    Optional<AuthorEntity> findById(Long id);

//    @Query("SELECT a.bookEntities FROM AuthorEntity a JOIN FETCH a.bookEntities WHERE a.id= :id")
//    List<BookEntity> findAllBooks(Long id);

}
