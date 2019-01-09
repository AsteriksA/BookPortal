package com.gold.service.interfaces;

import com.gold.model.Book;
import com.gold.model.Publisher;

import java.util.List;

public interface PublisherService extends BaseService<Publisher, Long> {

    Publisher findByName(String name);

    List<Book> findAllBooks(Long id);
}
