package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDto {

    @JsonView(View.Public.class)
    private String name;

    @JsonView(View.Public.class)
    private Set<AuthorDto> authors;

    @JsonView(View.Public.class)
    private GenreDto genre;

    @JsonView(View.Public.class)
    private PublisherDto publisher;
}

