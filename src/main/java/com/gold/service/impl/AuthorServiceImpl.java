package com.gold.service.impl;

import com.gold.dto.AuthorDto;
import com.gold.model.Author;
import com.gold.repository.AuthorRepository;
import com.gold.service.interfaces.AuthorService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private MapperUtils mapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, MapperUtils mapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorDto> findAll() {
        return mapper.convertToListDto(authorRepository.findAll(), AuthorDto.class);
    }

    @Override
    @Transactional
    public void add(AuthorDto author) {
        authorRepository.save(mapper.convertToEntity(author, Author.class));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, AuthorDto author) {
        Author entity = getAuthor(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(author, entity);
        authorRepository.save(entity);
    }

    //    TODO: change the method
    @Override
    public List<AuthorDto> findByFirstNameAndSecondName(String firstName, String secondName) {
        List<Author> authors = authorRepository.findByFirstNameOrSecondName(firstName, secondName);
        return mapper.convertToListDto(authors, AuthorDto.class);
    }

    @Override
    public AuthorDto findById(Long id) {
        return mapper.convertToDto(getAuthor(id), AuthorDto.class);
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
}
