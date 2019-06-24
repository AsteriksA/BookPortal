package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.PublisherEntity;
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
public class Publisher {

    @JsonView(View.Internal.class)
    private Long id;
    private String name;

    public static Publisher from(PublisherEntity entity) {
        return Publisher.builder()
                .name(entity.getName())
                .build();
    }
}
