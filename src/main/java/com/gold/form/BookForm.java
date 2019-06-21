package com.gold.form;

import com.gold.dto.Author;
import com.gold.dto.Genre;
import com.gold.dto.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookForm {

    private String name;
    private Set<Author> authors;
    private Genre genre;
    private Publisher publisher;
    private Date publisherYear;
    private int pageCount;
    private String isbn;
    private String description;
}

