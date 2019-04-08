package com.gold.service.interfaces;

import com.gold.dto.BookContent;

public interface BookContentService extends BaseService<BookContent, Long> {

    void update(Long id, BookContent content);
}
