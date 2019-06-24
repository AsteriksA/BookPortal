package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.BookEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Date publisherYear;
    private byte[] image;
    private int pageCount;
    private String isbn;
    private String description;
    private Double rating;
    private Integer voteCount;

    public static Book from(BookEntity entity) {
        return Book.builder()
                .name(entity.getName())
                .authors(Author.from(entity.getAuthors()))
                .genre(Genre.from(entity.getGenre()))
                .publisher(Publisher.from(entity.getPublisher()))
                .publisherYear(entity.getPublisherYear())
                .image(entity.getImage())
                .pageCount(entity.getPageCount())
                .isbn(entity.getIsbn())
                .description(entity.getDescription())
                .rating(entity.getRating())
                .voteCount(entity.getVoteCount())
                .build();
    }

    public static List<Book> from(List<BookEntity> entities) {
        return entities.stream()
                .map(Book::from)
                .collect(Collectors.toList());
    }

}

