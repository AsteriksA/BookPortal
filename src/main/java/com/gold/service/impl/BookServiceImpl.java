package com.gold.service.impl;

import com.gold.dto.BookDto;
import com.gold.model.Book;
import com.gold.repository.BookRepository;
import com.gold.service.interfaces.BookService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private MapperUtils mapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, MapperUtils mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return mapper.convertToListDto(books, BookDto.class);
    }

    @Override
    public List<BookDto> findByName(String name) {
        List<Book> books = bookRepository.findByNameLike(name);
        return mapper.convertToListDto(books, BookDto.class);
    }

//    TODO: change this method
    @Override
    public List<BookDto> findByNameFromSearch(String searchName) {
        List<Book> books = bookRepository.findByNameFromSearch(searchName);
        return mapper.convertToListDto(books, BookDto.class);
    }

    @Override
    public List<BookDto> findByGenre(String genreName) {
        List<Book> books = bookRepository.findByGenre_Name(genreName);
        return mapper.convertToListDto(books, BookDto.class);
    }

    @Override
    public List<BookDto> findByPublisher(String publisherName) {
        List<Book> books = bookRepository.findByPublisher_Name(publisherName);
        return mapper.convertToListDto(books, BookDto.class);
    }

    @Override
    public BookDto findById(Long id) {
        Book book = getEntity(id);
        return mapper.convertToDto(book, BookDto.class);
    }

    @Override
    @Transactional
    public void add(BookDto book) {
        Book entity = mapper.convertToEntity(book, Book.class);
        bookRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, BookDto book) {
        Book entity = getEntity(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(book, entity);
        bookRepository.save(entity);
    }

    private Book getEntity(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
