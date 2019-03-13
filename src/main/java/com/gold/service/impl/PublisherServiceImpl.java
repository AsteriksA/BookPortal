package com.gold.service.impl;

import com.gold.dto.Publisher;
import com.gold.model.PublisherEntity;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.PublisherService;
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
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final EntityMapper mapper;

    @Override
    public List<Publisher> findAll() {
        List<PublisherEntity> publisherEntities = publisherRepository.findAll();
        return mapper.convertToDto(publisherEntities, Publisher.class);
    }

    @Override
    public Publisher findByName(String name) {
        PublisherEntity publisherEntity = publisherRepository.findByName(name);
        return mapper.convertToDto(publisherEntity, Publisher.class);
    }

    @Override
    public Publisher findOne(Long id) {
        PublisherEntity publisherEntity = getPublisher(id);
        return mapper.convertToDto(publisherEntity, Publisher.class);
    }

    @Override
    @Transactional
    public void add(Publisher publisher) {
        PublisherEntity entity = mapper.convertToEntity(publisher, PublisherEntity.class);
        publisherRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, Publisher publisher) {
        PublisherEntity entity = getPublisher(id);
        EntityUtils.isNull(entity);
        mapper.convertToEntity(publisher, entity);
        publisherRepository.save(entity);
    }

    private PublisherEntity getPublisher(Long id) {
        return publisherRepository.findById(id).orElse(null);
    }
}
