package com.gold.service.interfaces;

import com.gold.dto.GenreDto;

public interface GenreService extends BaseService<GenreDto, Long> {

    GenreDto findByName(String name);
}
