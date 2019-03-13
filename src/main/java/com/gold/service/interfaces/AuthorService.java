package com.gold.service.interfaces;

import com.gold.dto.Author;

import java.util.List;

public interface AuthorService extends BaseService<Author, Long> {

    List<Author> findByFirstNameAndSecondName(String firstName, String secondName);
}
