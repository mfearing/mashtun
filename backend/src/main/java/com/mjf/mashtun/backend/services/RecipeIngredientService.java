package com.mjf.mashtun.backend.services;

import com.mjf.mashtun.backend.daos.RecipeIngredientDAO;
import com.mjf.mashtun.backend.dtos.RecipeIngredientDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class RecipeIngredientService {

    RecipeIngredientDAO recipeIngredientDAO;

    public List<RecipeIngredientDTO> getByRecipeId(long id){
        return recipeIngredientDAO.findByRecipeId(id);
    }

    @Transactional
    public int createRecipeIngredient(List<RecipeIngredientDTO> recipeIngredientDTOs){
        if(recipeIngredientDTOs.isEmpty())
            return 0;
        return recipeIngredientDAO.createRecipeIngredients(recipeIngredientDTOs);
    }

}
