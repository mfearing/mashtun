package com.mjf.mashtun.backend.services;

import com.mjf.mashtun.backend.daos.IngredientDAO;
import com.mjf.mashtun.backend.dtos.IngredientDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private static final Logger log = LoggerFactory.getLogger(IngredientService.class);
    private final IngredientDAO ingredientDAO;

    public List<IngredientDTO> getIngredients(){
        return ingredientDAO.findAll();
    }

    public List<IngredientDTO> getIngredientsPaginated(int pageNumber, int pageSize){
        if(pageNumber < 1 || pageSize < 0){
            throw new AppException("Invalid page number or page size", HttpStatus.BAD_REQUEST);
        }
        return ingredientDAO.findAllPaginated(pageNumber, pageSize);
    }

    public IngredientDTO getIngredientById(long id){
        return ingredientDAO.findById(id);
    }

    public IngredientDTO getIngredientByLabel(String label){
        return ingredientDAO.findByIngredientLabel(label);
    }

    @Transactional
    public IngredientDTO createIngredient(IngredientDTO ingredientDTO){
        return ingredientDAO.create(ingredientDTO);
    }

    @Transactional
    public int updateIngredient(IngredientDTO ingredientDTO){
        if(ingredientDAO.findById(ingredientDTO.getId()) == null){
            throw new AppException("Update failed - ingredient could not be found", HttpStatus.BAD_REQUEST);
        }
        return ingredientDAO.update(ingredientDTO);
    }

    @Transactional
    public int deleteIngredient(long id){

        IngredientDTO ingredientToDelete = ingredientDAO.findById(id);
        if(ingredientToDelete == null){
            throw new AppException("Delete failed - ingredient could not be found", HttpStatus.BAD_REQUEST);
        }
        try {
            return ingredientDAO.delete(ingredientToDelete.getId());
        } catch (DataAccessException e){
            log.debug(e.getMessage());
            throw new AppException("Delete failed", HttpStatus.BAD_REQUEST);
        }
    }


}
