package com.gold.service.impl;

import com.gold.model.Book;
import com.gold.model.Publisher;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PublisherServiceImpl implements PublisherService {

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher findByName(String name) {
        return publisherRepository.findByName(name);
    }

    @Override
    public List<Book> getAllBooks(Long id) {
        return publisherRepository.getAllBooks(id);
    }
}
