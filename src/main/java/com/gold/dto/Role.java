package com.gold.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gold.view.View;
import lombok.Data;

@Data
@JsonView(View.Internal.class)
public class Role {

    private String role;
}
