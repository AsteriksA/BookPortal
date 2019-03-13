package com.gold.service.impl;

import com.gold.dto.Genre;
import com.gold.model.GenreEntity;
import com.gold.repository.GenreRepository;
import com.gold.service.interfaces.GenreService;
import com.gold.util.EntityUtils;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final EntityMapper mapper;

    @Override
    public List<Genre> findAll() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        return mapper.convertToDto(genreEntities, Genre.class);
    }

    @Override
    public Genre findByName(String name) {
        GenreEntity genreEntity = genreRepository.findByName(name);
        return mapper.convertToDto(genreEntity, Genre.class);
    }


    @Override
    public Genre findOne(Long id) {
        GenreEntity genreEntity = getGenre(id);
        return mapper.convertToDto(genreEntity, Genre.class);
    }

    @Override
    @Transactional
    public void add(Genre genre) {
        GenreEntity entity = mapper.convertToEntity(genre, GenreEntity.class);
        genreRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, Genre genre) {
        GenreEntity entity = getGenre(id);
        EntityUtils.isNull(entity);
        mapper.convertToEntity(genre, entity);
        genreRepository.save(entity);
    }

    private GenreEntity getGenre(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
