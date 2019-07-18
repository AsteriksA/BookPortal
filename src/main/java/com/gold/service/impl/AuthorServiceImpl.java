package com.gold.service.impl;

import com.gold.dto.Author;
import com.gold.model.AuthorEntity;
import com.gold.repository.AuthorRepository;
import com.gold.service.interfaces.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final EntityMapper mapper;

    @Override
    public List<Author> findAll() {
        return mapper.convertToDto(authorRepository.findAll(), Author.class);
    }

    @Override
    @Transactional
    public Author add(Author author) {
        AuthorEntity entity = authorRepository.save(mapper.convertToEntity(author, AuthorEntity.class));
        return mapper.convertToDto(entity, Author.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Author update(Long id, Author author) {
        AuthorEntity entity = getAuthor(id);
        notNull(entity);
        mapper.convertToEntity(author, entity);
        return mapper.convertToDto(authorRepository.save(entity), Author.class);
    }

    //TODO: change the method. Maybe delete this method, cause a method findBooksByParam() is existed in a bookService.
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
