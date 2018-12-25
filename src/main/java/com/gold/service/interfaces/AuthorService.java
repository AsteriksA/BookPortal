package com.gold.service.interfaces;

import com.gold.model.Author;
import com.gold.model.Book;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getByFirstNameAndSecondName(String firstName, String secondName);

    Author getById(Long id);

    List<Book> getAllBooks(Long id);

}
