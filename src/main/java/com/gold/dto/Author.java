package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {

    private Long id;

    @JsonView(View.Public.class)
    private String firstName;

    @JsonView(View.Public.class)
    private String secondName;
}
