package com.gold.service.interfaces;

import com.gold.dto.Book;

import java.util.List;

public interface BookService extends BaseService<Book, Long> {

    List<Book> findByName(String name);

    List<Book> findByGenre(String genreName);

    List<Book> findByPublisher(String publisherName);

    List<Book> findByAuthor(String authorName);

    void update(Long id, Book book);

    void changeRating(Long id, Integer rating);

    List<Book> findBySearch(String param);
}
