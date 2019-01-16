package com.gold.service.impl;

import com.gold.dto.PublisherDto;
import com.gold.model.Publisher;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.PublisherService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PublisherServiceImpl implements PublisherService {

    private PublisherRepository publisherRepository;

    private MapperUtils mapper;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, MapperUtils mapper) {
        this.publisherRepository = publisherRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PublisherDto> findAll() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream()
                .map(publisher -> mapper.convertToDto(publisher, PublisherDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDto findByName(String name) {
        Publisher publisher = publisherRepository.findByName(name);
        return mapper.convertToDto(publisher, PublisherDto.class);
    }

    @Override
    public PublisherDto findById(Long id) {
        Publisher publisher = getPublisher(id);
        return mapper.convertToDto(publisher, PublisherDto.class);
    }

    @Override
    @Transactional
    public void add(PublisherDto publisher) {
        Publisher entity = mapper.convertToEntity(publisher, Publisher.class);
        publisherRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, PublisherDto publisher) {
        Publisher entity = getPublisher(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(publisher, entity);
        publisherRepository.save(entity);
    }

    private Publisher getPublisher(Long id) {
        return publisherRepository.findById(id).orElse(null);
    }
}
