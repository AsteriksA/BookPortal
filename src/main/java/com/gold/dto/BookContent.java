package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.BookContentEntity;
import com.gold.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.Public.class)
public class BookContent {

//    @JsonView(View.Internal.class)
    private Long id;
    private byte[] content;

    public static BookContent from(BookContentEntity entity) {
        return BookContent.builder()
                .content(entity.getContent())
                .build();
    }
}
