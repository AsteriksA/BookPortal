package com.gold.service.interfaces;

import com.gold.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findByName(String name);

    List<Book> findByAuthors(String author);

    List<Book> findByGenre(String genre);

    byte[] getContent(Long id);

//    @Query("SELECT b FROM Book b JOIN FETCH b.content WHERE b.id = (:id)")
//    Book findByIdAndFetchContentEagerly(@Param("id") Long id);




}
