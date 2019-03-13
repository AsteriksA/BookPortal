package com.gold.service.impl;

import com.gold.dto.Author;
import com.gold.model.AuthorEntity;
import com.gold.repository.AuthorRepository;
import com.gold.service.interfaces.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final EntityMapper mapper;

    @Override
    public List<Author> findAll() {
        return mapper.convertToDto(authorRepository.findAll(), Author.class);
    }

    @Override
    @Transactional
    public void add(Author author) {
        authorRepository.save(mapper.convertToEntity(author, AuthorEntity.class));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, Author author) {
        AuthorEntity entity = getAuthor(id);
        EntityUtils.isNull(entity);
        mapper.convertToEntity(author, entity);
        authorRepository.save(entity);
    }

    //    TODO: change the method
    @Override
    public List<Author> findByFirstNameAndSecondName(String firstName, String secondName) {
        List<AuthorEntity> authorEntities = authorRepository.findByFirstNameOrLastName(firstName, secondName);
        return mapper.convertToDto(authorEntities, Author.class);
    }

    @Override
    public Author findOne(Long id) {
        return mapper.convertToDto(getAuthor(id), Author.class);
    }

    private AuthorEntity getAuthor(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
}
