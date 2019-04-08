package com.gold.service.interfaces;

import com.gold.dto.Author;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorService extends BaseService<Author, Long> {

    @Transactional
    void update(Long id, Author author);

    List<Author> findByFirstNameAndSecondName(String firstName, String secondName);
}
