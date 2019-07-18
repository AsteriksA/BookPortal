package com.gold.service.impl;

import com.gold.dto.Book;
import com.gold.model.BookEntity;
import com.gold.model.GenreEntity;
import com.gold.model.PublisherEntity;
import com.gold.repository.AuthorRepository;
import com.gold.repository.BookRepository;
import com.gold.repository.GenreRepository;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.BookService;
import com.gold.util.EntityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private EntityMapper entityMapper;
    private BookEntity entity;
    private Book book;
    private Long bookId = 1L;

    @Before
    public void setUp(){
        bookService =
                new BookServiceImpl(bookRepository, authorRepository, genreRepository, publisherRepository, entityMapper);
        entity = new BookEntity();
        book = new Book();
    }

    @Test
    public void successfulFindAllBooks() {
        List<BookEntity> entities = Arrays.asList(new BookEntity(), new BookEntity());
        List<Book> books = Arrays.asList(new Book(), new Book());

        when(bookRepository.findAll()).thenReturn(entities);
        when(entityMapper.convertToDto(entities, Book.class)).thenReturn(books);

        assertEquals(2, bookService.findAll().size());

        verify(bookRepository, times(1)).findAll();
        verify(entityMapper, times(1)).convertToDto(entities,Book.class);
    }

    @Test
    public void successfulAddBook() throws IOException {
        Book bookForm = new Book();
        entity.setAuthors(Collections.emptySet());
        entity.setGenre(new GenreEntity());
        entity.setPublisher(new PublisherEntity());
        byte[] imageContent = new byte[10];
        byte[] fileContent = new byte[100];
        MultipartFile image = new MockMultipartFile("image", imageContent);
        MultipartFile file = new MockMultipartFile("file", fileContent);

        when(entityMapper.convertToEntity(bookForm, BookEntity.class)).thenReturn(entity);
        when(bookRepository.save(entity)).thenReturn(entity);
        when(entityMapper.convertToDto(entity, Book.class)).thenReturn(book);

        bookService.add(bookForm, image, file);

        verify(bookRepository, times(1)).save(entity);
    }

    @Test
    public void successfulRemoveBook() {
        bookService.delete(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void successfulUpdateBook() {
        String bookName = "Oliver Twist";
        book.setName(bookName);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(entity));
        when(entityMapper.convertToDto(entity, Book.class)).thenReturn(book);
        when(bookRepository.save(entity)).thenReturn(entity);

        assertEquals(bookName,bookService.update(bookId, book).getName());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(entity);
    }

    @Test
    public void successfulChangeRatingBook() {
        Integer voiceForRating = 3;
        Double rating =4.8;
        Integer voteCount =5;
        entity.setRating(rating);
        entity.setVoteCount(voteCount);

        double newRating = (rating * voteCount + voiceForRating) / ++voteCount;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(entity));
        when(bookRepository.save(entity)).thenReturn(entity);

        bookService.changeRating(bookId, voiceForRating);

        assertEquals( Double.valueOf(newRating),Double.valueOf(entity.getRating()));
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(entity);
    }

    @Test
    public void successfulSearchingBook() {
        String searchText = "some words";
        bookService.findBooksByParam(searchText);
        verify(bookRepository, times(1)).findByParam(searchText.split(" "));
    }

    @Test
    public void successfulFindBookByName() {
        String name = "Sherlock Holmes";

        bookService.findByName(name);
        verify(bookRepository, times(1)).findByNameLike(name);
    }

    @Test
    public void successfulFindOneBookById() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(entity));

        bookService.findOne(bookId);
        verify(bookRepository, times(1)).findById(bookId);
    }

}