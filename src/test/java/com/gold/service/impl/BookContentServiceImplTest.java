package com.gold.service.impl;

import com.gold.dto.BookContent;
import com.gold.model.BookContentEntity;
import com.gold.repository.BookContentRepository;
import com.gold.service.interfaces.BookContentService;
import com.gold.util.EntityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookContentServiceImplTest {

    private BookContentService bookContentService;

    @Mock
    private BookContentRepository bookContentRepository;
    @Mock
    private EntityMapper entityMapper;
    private BookContentEntity entity;
    private BookContent bookContent;
    private Long bookContentId = 1L;

    @Before
    public void setUp(){
        bookContentService = new BookContentServiceImpl(bookContentRepository, entityMapper);
        entity = new BookContentEntity();
        bookContent = new BookContent();
    }

    @Test
    public void successfulAddBookContent() {
        when(entityMapper.convertToEntity(bookContent, BookContentEntity.class)).thenReturn(entity);
        when(bookContentRepository.save(entity)).thenReturn(entity);
        when(entityMapper.convertToDto(entity, BookContent.class)).thenReturn(bookContent);

        bookContentService.add(bookContent);

        verify(bookContentRepository, times(1)).save(entity);
    }

    @Test
    public void successfulRemoveBookContent() {
        when(bookContentRepository.findById(bookContentId)).thenReturn(Optional.of(entity));

        bookContentService.delete(bookContentId);
        verify(bookContentRepository, times(1)).delete(entity);
    }

    @Test
    public void successfulUpdateBookContent() {
        when(bookContentRepository.findById(bookContentId)).thenReturn(Optional.of(entity));
        when(bookContentRepository.save(entity)).thenReturn(entity);

        bookContentService.update(bookContentId, bookContent);

        verify(bookContentRepository, times(1)).findById(bookContentId);
        verify(bookContentRepository, times(1)).save(entity);
    }

    @Test
    public void successfulFindOneBookContentById() {
        bookContentService.findOne(bookContentId);
        verify(bookContentRepository, times(1)).findById(bookContentId);
    }
}