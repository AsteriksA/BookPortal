package com.gold.service.impl;

import com.gold.dto.Book;
import com.gold.form.UpdateUserForm;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
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
        log.info("info get all books");
        log.debug("debug get all books");
        log.warn("warn get all books");
        return mapper.convertToDto(bookEntities, Book.class);
    }

    @Override
    public List<Book> findByName(String name) {
        List<BookEntity> bookEntities = bookRepository.findByNameLike(name);
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
    public List<Book> findByAuthor(String authorName) {
        return null;
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

    @Override
    @Transactional
    public void changeRating(Long id, Integer rating) {
        BookEntity entity = getEntity(id);
        Integer voteCount = entity.getVoteCount();
        Double ratingCandidate = calculateRating(entity, voteCount, rating);
        entity.setRating(ratingCandidate);
        entity.setVoteCount(voteCount);
        bookRepository.save(entity);
    }

    private Double calculateRating(BookEntity entity, Integer voteCount, Integer rating) {
          return (entity.getRating()*voteCount+rating)/++voteCount;
    }

    private BookEntity getEntity(Long id) {
//        return bookRepository.findById(id).orElse(null);
        return bookRepository.findById(id).
                orElseThrow(()->new UsernameNotFoundException("User doesn't exist"));
    }

    @Override
    public List<Book> findBySearch(String param) {
        String[] params = param.split(" ");
        List<BookEntity> books = bookRepository.findBySearch(params);
        return mapper.convertToDto(books, Book.class);
    }
}
