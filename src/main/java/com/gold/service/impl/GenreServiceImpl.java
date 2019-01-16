package com.gold.service.impl;

import com.gold.dto.GenreDto;
import com.gold.model.Genre;
import com.gold.repository.GenreRepository;
import com.gold.service.interfaces.GenreService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    private MapperUtils mapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, MapperUtils mapper) {
        this.genreRepository = genreRepository;
        this.mapper = mapper;
    }

    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genre -> mapper.convertToDto(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GenreDto findByName(String name) {
        Genre genre = genreRepository.findByName(name);
        return mapper.convertToDto(genre, GenreDto.class);
    }


    @Override
    public GenreDto findById(Long id) {
        Genre genre = getGenre(id);
        return mapper.convertToDto(genre, GenreDto.class);
    }

    @Override
    @Transactional
    public void add(GenreDto genre) {
        Genre entity = mapper.convertToEntity(genre, Genre.class);
        genreRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, GenreDto genre) {
        Genre entity = getGenre(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(genre, entity);
        genreRepository.save(entity);
    }

    private Genre getGenre(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
