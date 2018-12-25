package com.gold.service.impl;

import com.gold.model.Author;
import com.gold.model.Book;
import com.gold.repository.AuthorRepository;
import com.gold.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

//    TODO: check return value if the first or second argument will be ""
    @Override
    public Author getByFirstNameAndSecondName(String firstName, String secondName) {
        return authorRepository.findByFirstNameLikeOrSecondNameLike(firstName, secondName);
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks(Long id) {
        return authorRepository.getAllBooks(id);
    }
}
