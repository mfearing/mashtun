package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.IngredientDTO;

import java.util.List;

public interface IngredientDAO {
    List<IngredientDTO> findAll();
    List<IngredientDTO> findAllPaginated(int pageNumber, int pageSize);
    IngredientDTO findById(long id);
    IngredientDTO findByIngredientLabel(String label);
    IngredientDTO create(IngredientDTO ingredientDTO);
    int update(IngredientDTO ingredientDTO);
    int delete(long id);
}
