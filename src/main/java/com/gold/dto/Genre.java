package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.GenreEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {

    @JsonView(View.Public.class)
    private String name;

    public static Genre from(GenreEntity entity) {
        return Genre.builder()
                .name(entity.getName())
                .build();
    }
}
