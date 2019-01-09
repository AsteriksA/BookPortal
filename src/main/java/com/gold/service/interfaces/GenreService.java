package com.gold.service.interfaces;

import com.gold.model.Book;
import com.gold.model.Genre;

import java.util.List;

public interface GenreService extends BaseService<Genre, Long> {

    Genre findByName(String name);

    List<Book> findAllBooks(Long id);
}
