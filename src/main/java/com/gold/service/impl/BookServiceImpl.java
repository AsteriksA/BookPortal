package com.gold.service.impl;

import com.gold.dto.Book;
import com.gold.model.AuthorEntity;
import com.gold.model.BookContentEntity;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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
        return mapper.convertToDto(bookRepository.findAll(), Book.class);
    }

//    TODO: Delete this method? because there is already a universal method for finding the title of the book and the author
    @Override
    public List<Book> findByName(String name) {
        return mapper.convertToDto(bookRepository.findByNameLike(name), Book.class);
    }

//    TODO: Delete this method? because there is already a universal method for finding the title of the book and the author
    @Override
    public List<Book> findByAuthor(String authorName) {
        return null;
    }

    @Override
    public List<Book> findByGenre(String genreName) {
        return mapper.convertToDto(bookRepository.findByGenre_Name(genreName), Book.class);
    }

//    TODO: Delete this method? I’m unlikely to implement publisher search functionality
    @Override
    public List<Book> findByPublisher(String publisherName) {
        return mapper.convertToDto(bookRepository.findByPublisher_Name(publisherName), Book.class);
    }

    @Override
    public Book findOne(Long id) {
        BookEntity bookEntity = getEntity(id);
        return mapper.convertToDto(bookEntity, Book.class);
    }

    @Override
    @Transactional
    public void add(String book, MultipartFile imageFile, MultipartFile contentFile){
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public Book add(Book book, MultipartFile imageFile, MultipartFile contentFile) throws IOException {
        BookEntity entityCandidate = mapper.convertToEntity(book, BookEntity.class);
        byte[] image = imageFile.getBytes();
        byte[] content = contentFile.getBytes();
        build(image, content, entityCandidate);
        return mapper.convertToDto(bookRepository.save(entityCandidate), Book.class);
    }

    private void build(byte[] image, byte[] content, BookEntity entityCandidate) {
        entityCandidate.setImage(image);
        entityCandidate.setContent(setBookContent(content, entityCandidate));
        setAuthors(entityCandidate);
        setGenre(entityCandidate);
        setPublisher(entityCandidate);
    }

    private BookContentEntity setBookContent(byte[] content, BookEntity entityCandidate) {
        BookContentEntity bookContent = new BookContentEntity();
        bookContent.setContent(content);
        bookContent.setBookEntity(entityCandidate);
        return bookContent;
    }

    @Override
    @Transactional
    public Book add(Book book) {
        BookEntity entityCandidate = mapper.convertToEntity(book, BookEntity.class);
        setAuthors(entityCandidate);
        setGenre(entityCandidate);
        setPublisher(entityCandidate);
        return mapper.convertToDto(bookRepository.save(entityCandidate), Book.class);
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
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Book update(Long id, Book book) {
        BookEntity entity = getEntity(id);
        EntityUtils.notNull(entity);
        mapper.convertToEntity(book, entity);
        return mapper.convertToDto(bookRepository.save(entity), Book.class);
    }

    @Override
    @Transactional
    public void changeRating(Long id, Integer rating) {
        BookEntity entity = getEntity(id);
        Integer voteCount = entity.getVoteCount();
        Double ratingCandidate = calculateRating(entity, voteCount, rating);
        entity.setRating(ratingCandidate);
        entity.setVoteCount(++voteCount);
        bookRepository.save(entity);
    }

    private Double calculateRating(BookEntity entity, Integer voteCount, Integer rating) {
          Double totalRating = entity.getRating()*voteCount+rating;
          voteCount++;
          return totalRating/voteCount;
    }

    private BookEntity getEntity(Long id) {
        return bookRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("Book doesn't exist"));
    }

    @Override
    public List<Book> findBooksByParam(String param) {
        if (param == null) {
            return this.findAll();
        }
        String[] params = param.split(" ");
        List<BookEntity> books = bookRepository.findByParam(params);
        return mapper.convertToDto(books, Book.class);
    }
}
