package com.gold.service.interfaces;

import com.gold.dto.Genre;

public interface GenreService extends BaseService<Genre, Long> {

    Genre findByName(String name);
    Genre update(Long id, Genre genre);
    Genre findByBookId(Long bookId);
}
