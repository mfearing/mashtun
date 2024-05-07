package com.mjf.mashtun.backend.services;

import com.mjf.mashtun.backend.daos.UnitDAO;
import com.mjf.mashtun.backend.dtos.IngredientDTO;
import com.mjf.mashtun.backend.dtos.UnitDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UnitService {

    private final Logger logger = LoggerFactory.getLogger(UnitService.class);
    UnitDAO unitDAO;

    public List<UnitDTO> getUnits(){
        return unitDAO.findAll();
    }

    public UnitDTO getUnitsById(long id){
        return unitDAO.findById(id);
    }

    public UnitDTO getUnitsByLabel(String label){
        return unitDAO.findByUnitLabel(label);
    }

    @Transactional
    public UnitDTO createUnit(UnitDTO unitDTO){
        return unitDAO.create(unitDTO);
    }

    @Transactional
    public int updateUnit(UnitDTO unitDTO){
        if(unitDAO.findById(unitDTO.getId()) == null){
            throw new AppException("Update failed - unit could not be found", HttpStatus.BAD_REQUEST);
        }
        return unitDAO.update(unitDTO);
    }

    @Transactional
    public int deleteUnit(long id){
        UnitDTO unitToDelete = unitDAO.findById(id);
        if(unitToDelete == null){
            throw new AppException("Delete failed - unit could not be found", HttpStatus.BAD_REQUEST);
        }
        try {
            return unitDAO.delete(unitToDelete.getId());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Delete failed", HttpStatus.BAD_REQUEST);
        }
    }

}
