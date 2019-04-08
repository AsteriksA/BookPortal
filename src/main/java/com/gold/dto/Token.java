package com.gold.dto;

import com.gold.model.TokenEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private String value;

    public static Token from(TokenEntity entity) {
        return new Token(entity.getValue());
    }
}
