package com.gold.service.interfaces;

import com.gold.dto.Publisher;

public interface PublisherService extends BaseService<Publisher, Long> {

    Publisher findByName(String name);
    Publisher update(Long id, Publisher publisher);
    Publisher findByBookId(Long bookId);
}
