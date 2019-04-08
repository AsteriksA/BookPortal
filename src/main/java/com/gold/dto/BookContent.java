package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.BookContentEntity;
import com.gold.view.View;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookContent {

    @JsonView(View.Public.class)
    private byte[] content;

    public static BookContent from(BookContentEntity entity) {
        return BookContent.builder()
                .content(entity.getContent())
                .build();
    }
}
