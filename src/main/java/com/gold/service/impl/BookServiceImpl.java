package com.gold.service.impl;

import com.gold.model.Book;
import com.gold.repository.BookRepository;
import com.gold.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByNameLike(name);
    }

    @Override
    public List<Book> findByAuthors(String author) {
        return bookRepository.findByAuthors(author);
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public byte[] getContent(Long id) {
        return bookRepository.findByIdAndFetchContentEagerly(id).getContent();
    }
}
