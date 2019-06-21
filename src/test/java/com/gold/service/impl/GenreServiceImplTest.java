package com.gold.service.impl;

import com.gold.dto.Author;
import com.gold.dto.Genre;
import com.gold.model.GenreEntity;
import com.gold.repository.GenreRepository;
import com.gold.service.interfaces.GenreService;
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
public class GenreServiceImplTest {

    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;
    @Mock
    private EntityMapper entityMapper;
    private GenreEntity entity;
    private Genre genre;
    private Long genreId = 1L;

    @Before
    public void setUp() throws Exception {
        genreService = new GenreServiceImpl(genreRepository, entityMapper);
        entity = new GenreEntity();
        genre = new Genre();
    }

    @Test
    public void successfulFindAllGenres() {
        List<GenreEntity> entities = Arrays.asList(new GenreEntity(), new GenreEntity());
        List<Genre> genres = Arrays.asList(new Genre(), new Genre());

        when(genreRepository.findAll()).thenReturn(entities);
        when(entityMapper.convertToDto(entities, Genre.class)).thenReturn(genres);

        assertEquals(2, genreService.findAll().size());
        verify(genreRepository, times(1)).findAll();
        verify(entityMapper, times(1)).convertToDto(entities,Genre.class);
    }

    @Test
    public void successfulAddGenre() {
        when(entityMapper.convertToEntity(genre, GenreEntity.class)).thenReturn(entity);
        when(genreRepository.save(entity)).thenReturn(entity);
        when(entityMapper.convertToDto(entity, Genre.class)).thenReturn(genre);

        genreService.add(genre);
        verify(genreRepository, times(1)).save(entity);
    }

    @Test
    public void successfulRemoveGenre() {
        genreService.remove(genreId);
        verify(genreRepository, times(1)).deleteById(genreId);
    }

    @Test
    public void successfulUpdateGenre() {
        String name = "Detective";
        genre.setName(name);

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(entity));
        when(entityMapper.convertToDto(entity, Genre.class)).thenReturn(genre);
        when(genreRepository.save(entity)).thenReturn(entity);

        assertEquals(name, genreService.update(genreId, genre).getName());
        
        verify(genreRepository, times(1)).findById(genreId);
        verify(genreRepository, times(1)).save(entity);
    }

    @Test
    public void successfulFindGenreByName() {
        String name = "Detective";
        entity.setName(name);
        genre.setName(name);

        genreService.findByName(name);
        
        verify(genreRepository, times(1)).findByName(name);
    }

    @Test
    public void successfulFindOneAuthorById() {
        genreService.findOne(genreId);
        verify(genreRepository, times(1)).findById(genreId);
    }
}