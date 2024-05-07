package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.RecipeDTO;

import java.util.List;

public interface RecipeDAO {
    List<RecipeDTO> findAll();
    RecipeDTO findById(long id);
    RecipeDTO findByName(String name);
    RecipeDTO create(RecipeDTO recipeDTO);
    int update(RecipeDTO recipeDTO);
    int delete(long id);
}
