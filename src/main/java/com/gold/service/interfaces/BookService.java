package com.gold.service.interfaces;

import com.gold.dto.BookDto;

import java.util.List;

public interface BookService extends BaseService<BookDto, Long> {

    List<BookDto> findByName(String name);

    List<BookDto> findByNameFromSearch(String searchName);

    List<BookDto> findByGenre(String genreName);

    List<BookDto> findByPublisher(String publisherName);
}
