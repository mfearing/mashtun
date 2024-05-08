package com.mjf.mashtun.backend.controllers;

import com.mjf.mashtun.backend.dtos.RecipeIngredientDTO;
import com.mjf.mashtun.backend.services.RecipeIngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class RecipeIngredientController {

    RecipeIngredientService recipeIngredientService;

    @GetMapping("/recipeIngredient")
    public List<RecipeIngredientDTO> getRecipeById(@RequestParam long id){
        return recipeIngredientService.getByRecipeId(id);
    }

    @PostMapping("/recipeIngredient")
    public ResponseEntity<String> createRecipeIngredient (@RequestBody List<RecipeIngredientDTO> recipeIngredientDTO){
        recipeIngredientService.createRecipeIngredient(recipeIngredientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
