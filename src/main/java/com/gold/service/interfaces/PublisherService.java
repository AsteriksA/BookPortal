package com.gold.service.interfaces;

import com.gold.dto.PublisherDto;

public interface PublisherService extends BaseService<PublisherDto, Long> {

    PublisherDto findByName(String name);
}
