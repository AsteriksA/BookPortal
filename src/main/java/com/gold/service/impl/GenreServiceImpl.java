package com.gold.service.impl;

import com.gold.model.Book;
import com.gold.model.Genre;
import com.gold.repository.GenreRepository;
import com.gold.service.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public List<Book> findAllBooks(Long id) {
        List books =null;
        Genre genre = findById(id);

        if (genre != null) {
            books = new ArrayList(genre.getBooks());
        }
        return books;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public void addEntity(Genre entity) {
        genreRepository.save(entity);
    }

    @Override
    public void removeEntity(Long id) {
        genreRepository.deleteById(id);
    }

//    TODO
    @Override
    public void updateEntity(Long id, Genre entity) {
        Genre oldGenre = findById(id);
        if (oldGenre != null) {

        }
    }
}
