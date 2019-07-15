package com.gold.service.impl;

import com.gold.dto.BookContent;
import com.gold.model.BookContentEntity;
import com.gold.repository.BookContentRepository;
import com.gold.service.interfaces.BookContentService;
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

public class BookContentServiceImpl implements BookContentService {

    private final BookContentRepository bookContentRepository;
    private final EntityMapper mapper;

     @Override
    public List<BookContent> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookContent findOne(Long id) {
        return mapper.convertToDto(getEntity(id), BookContent.class);
    }

//    TODO: Is this method necessary? Because the whole book is added in BookService with few arguments.
    @Override
    @Transactional
    public BookContent add(BookContent entity) {
       return mapper.convertToDto(bookContentRepository
               .save(mapper.convertToEntity(entity, BookContentEntity.class)), BookContent.class);
    }


//    TODO: How correct implement delete method? Like this. Get entity by id in the DB then invoke delete method.
//    Or implement like another methods in other test classes - right away invoke delete by id?
    @Override
    @Transactional
    public void delete(Long id) {
        bookContentRepository.delete(getEntity(id));
    }

    @Override
    @Transactional
    public void update(Long id, BookContent content) {
        BookContentEntity entity = getEntity(id);
        notNull(entity);
        mapper.convertToEntity(content, entity);
        bookContentRepository.save(entity);
    }

    private BookContentEntity getEntity(Long id) {
         return bookContentRepository.findById(id).orElse(null);
    }
}
