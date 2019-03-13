package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Book {

    private Long id;

    @JsonView(View.Public.class)
    private String name;

    @JsonView(View.Public.class)
    private Set<Author> authors;

    @JsonView(View.Public.class)
    private Genre genre;

    @JsonView(View.Public.class)
    private Publisher publisher;
}

