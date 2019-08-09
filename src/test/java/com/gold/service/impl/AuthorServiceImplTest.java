package com.gold.service.impl;

import com.gold.dto.Author;
import com.gold.model.AuthorEntity;
import com.gold.repository.AuthorRepository;
import com.gold.service.interfaces.AuthorService;
import com.gold.util.EntityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private EntityMapper entityMapper;
    private AuthorEntity entity;
    private Author author;
    private Long authorId = 1L;

    @Before
    public void setUp(){
        authorService = new AuthorServiceImpl(authorRepository, entityMapper);
        entity = new AuthorEntity();
        author = new Author();
    }

    @Test
    public void successfulFindAllAuthors() {
        List<AuthorEntity> entities = Arrays.asList(new AuthorEntity(), new AuthorEntity());
        List<Author> authors = Arrays.asList(new Author(), new Author());

        when(authorRepository.findAll()).thenReturn(entities);
        when(entityMapper.convertToDto(entities, Author.class)).thenReturn(authors);

        assertEquals(2, authorService.findAll().size());

        verify(authorRepository, times(1)).findAll();
        verify(entityMapper, times(1)).convertToDto(entities,Author.class);
    }

    @Test
    public void successfulAddAuthor() {
        when(entityMapper.convertToEntity(author, AuthorEntity.class)).thenReturn(entity);
        when(authorRepository.save(entity)).thenReturn(entity);
        when(entityMapper.convertToDto(entity, Author.class)).thenReturn(author);

        authorService.add(author);

        verify(authorRepository, times(1)).save(entity);
    }

    @Test
    public void successfulRemoveAuthor() {
        authorService.delete(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    public void successfulUpdateAuthor() {
        String lastName = "Chase";
        author.setLastName(lastName);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(entity));
        when(entityMapper.convertToDto(entity, Author.class)).thenReturn(author);
        when(authorRepository.save(entity)).thenReturn(entity);

        assertEquals(lastName, authorService.update(authorId, author).getLastName());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).save(entity);
    }

    @Test
    public void successfulFindAuthorByFirstAndLastName() {
        String firstName = "Artur";
        String lastName = "Doyle";

        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorService.findByFirstNameAndSecondName(firstName, lastName);

        verify(authorRepository, times(1)).findByFirstNameOrLastName(firstName, lastName);
    }

    @Test
    public void successfulFindOneAuthorById() {
        authorService.findOne(authorId);
        verify(authorRepository, times(1)).findById(authorId);
    }
}