package com.mjf.mashtun.backend.controllers;

import com.mjf.mashtun.backend.dtos.RecipeDTO;
import com.mjf.mashtun.backend.services.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class RecipeController {

    private RecipeService recipeService;

    @GetMapping("/recipe")
    public List<RecipeDTO> getAllRecipes(){
        return recipeService.getRecipes();
    }

    @GetMapping("/recipe/{id}")
    public RecipeDTO getRecipe(@PathVariable long id){
        return recipeService.getRecipeById(id);
    }

    @PostMapping("/recipe")
    public ResponseEntity<String> createRecipe(@RequestBody List<RecipeDTO> recipeDTO){
        recipeService.createRecipe(recipeDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO, @PathVariable long id){
        RecipeDTO recipeToUpdate = recipeService.getRecipeById(id);
        if(recipeToUpdate != null){
            recipeToUpdate.setRecipe_label(recipeDTO.getRecipe_label());
            recipeToUpdate.setDescription(recipeDTO.getDescription());
            recipeToUpdate.setInstructions(recipeDTO.getInstructions());
            recipeService.updateRecipe(recipeToUpdate);
            return ResponseEntity.ok(recipeToUpdate);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/recipe/{id}")
    public int deleteRecipe(@PathVariable long id){
        return recipeService.deleteRecipe(id);
    }

}
