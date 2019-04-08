package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.AuthorEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    private Long id;

    @JsonView(View.Public.class)
    private String firstName;

    @JsonView(View.Public.class)
    private String lastName;

    public static Author from(AuthorEntity entity) {
        return Author.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

    public static Set<Author> from(Set<AuthorEntity> entities) {
        return entities.stream()
                .map(Author::from)
                .collect(Collectors.toSet());
    }
}
