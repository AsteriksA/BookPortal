package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.AuthorEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.Public.class)
public class Author {

    @JsonView(View.Internal.class)
    private Long id;
    private String firstName;
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
