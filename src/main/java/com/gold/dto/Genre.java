package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.GenreEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.Public.class)
public class Genre {

    @JsonView(View.Internal.class)
    private Long id;
    private String name;

    public static Genre from(GenreEntity entity) {
        return Genre.builder()
                .name(entity.getName())
                .build();
    }
}
