package com.gold.service.impl;

import com.gold.model.Author;
import com.gold.model.Book;
import com.gold.repository.AuthorRepository;
import com.gold.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

//    TODO
//    public Author findByName(String name) {
//        findByFirstNameAndSecondName(name, null); /*or second argument must be "" ??*/
//    }

    @Override
    public void addEntity(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void removeEntity(Long id) {
        authorRepository.deleteById(id);
    }

//    TODO:
    @Override
    public void updateEntity(Long id, Author entity) {
        Author oldAuthor = findById(id);

        if (oldAuthor != null) {

        }
    }

    //    TODO: check return value if the first or second argument will be ""
    @Override
    public List<Author> findByFirstNameAndSecondName(String firstName, String secondName) {
        return authorRepository.findByFirstNameLikeOrSecondNameLike(firstName, secondName);
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findAllBooks(Long id) {
        List<Book> books = null;
        Author author = findById(id);

        if (author != null) {
            books = new ArrayList(author.getBooks());
        }
        return books;
    }
}
