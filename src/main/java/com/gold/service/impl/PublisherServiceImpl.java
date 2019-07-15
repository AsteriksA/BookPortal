package com.gold.service.impl;

import com.gold.dto.Publisher;
import com.gold.model.PublisherEntity;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.PublisherService;
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
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final EntityMapper mapper;

    @Override
    public List<Publisher> findAll() {
        return mapper.convertToDto(publisherRepository.findAll(), Publisher.class);
    }

    @Override
    public Publisher findByName(String name) {
        return mapper.convertToDto(publisherRepository.findByName(name), Publisher.class);
    }

    @Override
    public Publisher findOne(Long id) {
        return mapper.convertToDto(getPublisher(id), Publisher.class);
    }

    @Override
    @Transactional
    public Publisher add(Publisher publisher) {
        PublisherEntity entity = mapper.convertToEntity(publisher, PublisherEntity.class);
        return mapper.convertToDto(publisherRepository.save(entity), Publisher.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Publisher publisher) {
        PublisherEntity entity = getPublisher(id);
        notNull(entity);
        mapper.convertToEntity(publisher, entity);
        publisherRepository.save(entity);
    }

    private PublisherEntity getPublisher(Long id) {
        return publisherRepository.findById(id).orElse(null);
    }
}
