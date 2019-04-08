package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.model.PublisherEntity;
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
public class Publisher {

    @JsonView(View.Public.class)
    private String name;

    public static Publisher from(PublisherEntity entity) {
        return Publisher.builder()
                .name(entity.getName())
                .build();
    }
}
