package com.mjf.mashtun.backend.controllers;

import com.mjf.mashtun.backend.dtos.IngredientDTO;
import com.mjf.mashtun.backend.services.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class IngredientController {

    IngredientService ingredientService;

    @GetMapping("/ingredient")
    public List<IngredientDTO> getAllIngredients(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize){
        if(pageNumber == null || pageSize == null){
            return ingredientService.getIngredients();
        }
        return ingredientService.getIngredientsPaginated(pageNumber, pageSize);
    }

    @GetMapping("/ingredient/{id}")
    public IngredientDTO getIngredient(@PathVariable long id){
        return ingredientService.getIngredientById(id);
    }

    @PostMapping("/ingredient")
    public IngredientDTO createIngredient(@RequestBody IngredientDTO ingredientDTO){
        return ingredientService.createIngredient(ingredientDTO);
    }

    @PutMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@RequestBody IngredientDTO ingredientDTO, @PathVariable long id){
        IngredientDTO ingredientToUpdate = ingredientService.getIngredientById(id);
        if(ingredientToUpdate != null){
            ingredientToUpdate.setIngredientLabel(ingredientDTO.getIngredientLabel());
            ingredientService.updateIngredient(ingredientToUpdate);
            return ResponseEntity.ok(ingredientToUpdate);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/ingredient/{id}")
    public int deleteIngredient(@PathVariable long id){
        return ingredientService.deleteIngredient(id);
    }

}
