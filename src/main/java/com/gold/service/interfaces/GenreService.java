package com.gold.service.interfaces;

import com.gold.model.Book;
import com.gold.model.Genre;

import java.util.List;

public interface GenreService {

    Genre findByName(String name);

    List<Book> getAllBooks(Long id);
}
