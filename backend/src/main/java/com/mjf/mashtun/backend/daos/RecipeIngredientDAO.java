package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.RecipeIngredientDTO;

import java.util.List;

public interface RecipeIngredientDAO {
    List<RecipeIngredientDTO> findByRecipeId(long id);
    int createRecipeIngredients(List<RecipeIngredientDTO> recipeIngredientDTOs);
}
