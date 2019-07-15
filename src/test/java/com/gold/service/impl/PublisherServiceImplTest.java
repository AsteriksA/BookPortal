package com.gold.service.impl;

import com.gold.dto.Publisher;
import com.gold.model.PublisherEntity;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.PublisherService;
import com.gold.util.EntityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PublisherServiceImplTest {
   
    private PublisherService publisherService;

    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private EntityMapper entityMapper;
    private PublisherEntity entity;
    private Publisher publisher;
    private Long publisherId = 1L;

    @Before
    public void setUp(){
        publisherService = new PublisherServiceImpl(publisherRepository, entityMapper);
        entity = new PublisherEntity();
        publisher = new Publisher();
    }

    @Test
    public void successfulFindAllPublisher() {
        List<PublisherEntity> entities = Arrays.asList(new PublisherEntity(), new PublisherEntity());
        List<Publisher> publishers = Arrays.asList(new Publisher(), new Publisher());
        
        when(publisherRepository.findAll()).thenReturn(entities);
        when(entityMapper.convertToDto(entities, Publisher.class)).thenReturn(publishers);
        
        assertEquals(2, publisherService.findAll().size());
        
        verify(publisherRepository, times(1)).findAll();
        verify(entityMapper, times(1)).convertToDto(entities,Publisher.class);
    }

    @Test
    public void successfulAddPublisher() {
        when(entityMapper.convertToEntity(publisher, PublisherEntity.class)).thenReturn(entity);
        when(publisherRepository.save(entity)).thenReturn(entity);
        when(entityMapper.convertToDto(entity, Publisher.class)).thenReturn(publisher);

        publisherService.add(publisher);

        verify(publisherRepository, times(1)).save(entity);
    }

    @Test
    public void successfulRemoveGenre() {
        publisherService.delete(publisherId);
        verify(publisherRepository, times(1)).deleteById(publisherId);
    }

    @Test
    public void successfulFindPublisherByName() {
        String name = "Union";
        entity.setName(name);
        publisher.setName(name);

        publisherService.findByName(name);
        verify(publisherRepository, times(1)).findByName(name);
    }

    @Test
    public void successfulFindOnePublisherById() {
        publisherService.findOne(publisherId);
        verify(publisherRepository, times(1)).findById(publisherId);
    }
}