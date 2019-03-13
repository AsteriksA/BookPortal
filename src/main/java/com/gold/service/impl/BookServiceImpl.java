package com.gold.service.impl;

import com.gold.dto.Book;
import com.gold.model.AuthorEntity;
import com.gold.model.BookEntity;
import com.gold.model.GenreEntity;
import com.gold.model.PublisherEntity;
import com.gold.repository.AuthorRepository;
import com.gold.repository.BookRepository;
import com.gold.repository.GenreRepository;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.BookService;
import com.gold.util.EntityUtils;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final EntityMapper mapper;

      @Override
    public List<Book> findAll() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return mapper.convertToDto(bookEntities, Book.class);
    }

    @Override
    public List<Book> findByName(String name) {
        List<BookEntity> bookEntities = bookRepository.findByNameLike(name);
        return mapper.convertToDto(bookEntities, Book.class);
    }

//    TODO: change this method
    @Override
    public List<Book> findByNameFromSearch(String searchName) {
        List<BookEntity> bookEntities = bookRepository.findByNameFromSearch(searchName);
        return mapper.convertToDto(bookEntities, Book.class);
    }

    @Override
    public List<Book> findByGenre(String genreName) {
        List<BookEntity> bookEntities = bookRepository.findByGenre_Name(genreName);
        return mapper.convertToDto(bookEntities, Book.class);
    }

    @Override
    public List<Book> findByPublisher(String publisherName) {
        List<BookEntity> bookEntities = bookRepository.findByPublisher_Name(publisherName);
        return mapper.convertToDto(bookEntities, Book.class);
    }

    @Override
    public Book findOne(Long id) {
        BookEntity bookEntity = getEntity(id);
        return mapper.convertToDto(bookEntity, Book.class);
    }

    @Override
    @Transactional
    public void add(Book book) {
        BookEntity entity = mapper.convertToEntity(book, BookEntity.class);
        setAuthors(entity);
        setGenre(entity);
        setPublisher(entity);
        bookRepository.save(entity);
    }

    private void setPublisher(BookEntity entity) {
        PublisherEntity sourcePublisher = entity.getPublisher();
        PublisherEntity persistPublisher = publisherRepository.findByName(sourcePublisher.getName());
        if (persistPublisher != null) {
            persistPublisher.getBooks().add(entity);
            entity.setPublisher(persistPublisher);
        }
    }

    private void setGenre(BookEntity entity) {
        GenreEntity sourceGenre = entity.getGenre();
        GenreEntity persistGenre = genreRepository.findByName(sourceGenre.getName());
        if (persistGenre != null) {
            persistGenre.getBooks().add(entity);
            entity.setGenre(persistGenre);
        }
    }

    private void setAuthors(BookEntity entity) {
        Set<AuthorEntity> sourceAuthors = entity.getAuthors();
        Set<AuthorEntity> authorEntities=new HashSet<>();

        for (AuthorEntity author : sourceAuthors) {
            AuthorEntity persistAuthor =
                    authorRepository.findByFirstNameAndLastName(author.getFirstName(),author.getLastName());
            if (persistAuthor != null) {
                persistAuthor.getBooks().add(entity);
                author = persistAuthor;
            } else {
                author.setBooks(new HashSet<>());
                author.getBooks().add(entity);
            }
            authorEntities.add(author);
        }
        entity.setAuthors(authorEntities);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, Book book) {
        BookEntity entity = getEntity(id);
        EntityUtils.isNull(entity);
        mapper.convertToEntity(book, entity);
        bookRepository.save(entity);
    }

    private BookEntity getEntity(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
