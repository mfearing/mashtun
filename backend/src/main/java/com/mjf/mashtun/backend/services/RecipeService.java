package com.mjf.mashtun.backend.services;

import com.mjf.mashtun.backend.daos.RecipeDAO;
import com.mjf.mashtun.backend.dtos.RecipeDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class RecipeService {

    private final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private RecipeDAO recipeDAO;

    public List<RecipeDTO> getRecipes(){
        return recipeDAO.findAll();
    }

    public RecipeDTO getRecipeById(long id){
        return recipeDAO.findById(id);
    }

    public RecipeDTO getRecipeByRecipeLabel(String recipe_label){
        return recipeDAO.findByRecipeLabel(recipe_label);
    }

    @Transactional
    public void createRecipe(List<RecipeDTO> recipeDTO){
        recipeDAO.create(recipeDTO);
    }

    @Transactional
    public int updateRecipe(RecipeDTO recipeDTO){
        if(recipeDAO.findById(recipeDTO.getId()) == null){
            throw new AppException("Update failed - recipe not found", HttpStatus.NOT_FOUND);
        }
        return recipeDAO.update(recipeDTO);
    }

    @Transactional
    public int deleteRecipe(long id){
        if(recipeDAO.findById(id) == null){
            throw new AppException("Delete failed - recipe not found", HttpStatus.NOT_FOUND);
        }
        return recipeDAO.delete(id);
    }


}
