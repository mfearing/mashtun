package com.mjf.mashtun.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredientDTO {

    private long id;
    private long recipe_id;
    private String recipe_label;
    private long ingredient_id;
    private String ingredient_label;
    private long unit_id;
    private String unit_label;
    private Float amount;

}
