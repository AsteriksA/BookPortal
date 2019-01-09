package com.gold.service.interfaces;

import com.gold.model.Author;
import com.gold.model.Book;

import java.util.List;

public interface AuthorService extends BaseService<Author, Long> {

    List<Author> findByFirstNameAndSecondName(String firstName, String secondName);

    List<Book> findAllBooks(Long id);

}
