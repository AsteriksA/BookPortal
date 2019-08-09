package com.gold.service.impl;

import com.gold.dto.Genre;
import com.gold.model.GenreEntity;
import com.gold.repository.GenreRepository;
import com.gold.service.interfaces.GenreService;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gold.util.EntityUtils.notNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final EntityMapper mapper;

    @Override
    public List<Genre> findAll() {
        return mapper.convertToDto(genreRepository.findAll(), Genre.class);
    }

    @Override
    public Genre findByName(String name) {
        return mapper.convertToDto(genreRepository.findByName(name), Genre.class);
    }

    @Override
    public Genre findByBookId(Long bookId) {
        return mapper.convertToDto(genreRepository.findByBookId(bookId), Genre.class);
    }

    @Override
    public Genre findOne(Long id) {
        return mapper.convertToDto(getGenre(id), Genre.class);
    }

    @Override
    @Transactional
    public Genre add(Genre genre) {
        GenreEntity entity = mapper.convertToEntity(genre, GenreEntity.class);
        return mapper.convertToDto(genreRepository.save(entity),Genre.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Genre update(Long id, Genre genre) {
        GenreEntity entity = getGenre(id);
        notNull(entity);
        mapper.convertToEntity(genre, entity);
        return mapper.convertToDto(genreRepository.save(entity), Genre.class);
    }

    private GenreEntity getGenre(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
