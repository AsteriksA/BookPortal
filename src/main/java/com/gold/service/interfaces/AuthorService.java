package com.gold.service.interfaces;

import com.gold.dto.Author;

import java.util.List;

public interface AuthorService extends BaseService<Author, Long> {

    Author update(Long id, Author author);
    List<Author> findByFirstNameAndSecondName(String firstName, String secondName);
}
