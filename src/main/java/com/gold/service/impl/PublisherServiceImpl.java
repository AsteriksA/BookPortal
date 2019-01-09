package com.gold.service.impl;

import com.gold.model.Book;
import com.gold.model.Publisher;
import com.gold.repository.PublisherRepository;
import com.gold.service.interfaces.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Book> findAllBooks(Long id) {
        List<Book> books= null;
        Publisher publisher = findById(id);

        if (publisher != null) {
            books = new ArrayList(publisher.getBooks());
        }
        return books;
    }

    @Override
    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher findById(Long id) {
        return publisherRepository.findById(id).orElse(null);
    }

    @Override
    public void addEntity(Publisher entity) {
        publisherRepository.save(entity);
    }

    @Override
    public void removeEntity(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public void updateEntity(Long id, Publisher entity) {
        throw new UnsupportedOperationException();
    }
}
