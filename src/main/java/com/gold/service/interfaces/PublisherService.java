package com.gold.service.interfaces;

import com.gold.dto.Publisher;

public interface PublisherService extends BaseService<Publisher, Long> {

    Publisher findByName(String name);
}
