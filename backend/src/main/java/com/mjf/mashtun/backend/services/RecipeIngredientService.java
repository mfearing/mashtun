package com.mjf.mashtun.backend.services;

import com.mjf.mashtun.backend.daos.RecipeIngredientDAO;
import com.mjf.mashtun.backend.dtos.RecipeIngredientDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public void createRecipeIngredient(List<RecipeIngredientDTO> recipeIngredientDTOs){
        if(recipeIngredientDTOs.isEmpty()) {
            throw new AppException("Recipe ingredients not created, list is empty", HttpStatus.BAD_REQUEST);
        }
        recipeIngredientDAO.createRecipeIngredients(recipeIngredientDTOs);
    }

}
