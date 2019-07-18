package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView({View.Public.class})
public class Book {

    @JsonView(View.Internal.class)
    private Long id;
    private String name;
    private Set<Author> authors;
    private Genre genre;
    private Publisher publisher;
    private Date publicationYear;
    private byte[] image;
    private int pageCount;
    private String isbn;
    private String description;
    private Double rating;
    private Integer voteCount;
}

