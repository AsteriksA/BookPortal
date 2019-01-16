package com.gold.service.impl;

import com.gold.dto.BookContentDto;
import com.gold.model.BookContent;
import com.gold.repository.BookContentRepository;
import com.gold.service.interfaces.BookContentService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookContentServiceImpl implements BookContentService {

    private BookContentRepository bookContentRepository;
    private MapperUtils mapper;

    public BookContentServiceImpl(BookContentRepository bookContentRepository, MapperUtils mapper) {
        this.bookContentRepository = bookContentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BookContentDto> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookContentDto findById(Long id) {
        return mapper.convertToDto(getEntity(id), BookContentDto.class);
    }

    @Override
    @Transactional
    public void add(BookContentDto entity) {
        bookContentRepository.save(mapper.convertToEntity(entity, BookContent.class));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        bookContentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, BookContentDto content) {
        BookContent entity = getEntity(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(content, entity);
        bookContentRepository.save(entity);
    }

    private BookContent getEntity(Long id) {
        return bookContentRepository.findById(id).orElse(null);
    }
}
