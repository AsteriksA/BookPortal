package com.gold.service.interfaces;

import com.gold.model.Book;
import com.gold.model.Genre;

import java.util.List;

public interface BookService extends BaseService<Book, Long> {

    List<Book> findByName(String name);

    List<Book> findByAuthors(String author);

    List<Book> findByGenre(Genre genre);

//    Book getWholeBook(Long id);

    byte[] getContent(Long id);



//    @Query("SELECT b FROM Book b JOIN FETCH b.content WHERE b.id = (:id)")
//    Book findByIdAndFetchContentEagerly(@Param("id") Long id);




}
