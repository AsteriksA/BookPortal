package com.gold.service.interfaces;

import com.gold.dto.Book;

import java.util.List;

public interface BookService extends BaseService<Book, Long> {

    List<Book> findByName(String name);

    List<Book> findByNameFromSearch(String searchName);

    List<Book> findByGenre(String genreName);

    List<Book> findByPublisher(String publisherName);
}
