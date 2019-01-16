package com.gold.service.interfaces;

import com.gold.dto.AuthorDto;

import java.util.List;

public interface AuthorService extends BaseService<AuthorDto, Long> {

    List<AuthorDto> findByFirstNameAndSecondName(String firstName, String secondName);
}
