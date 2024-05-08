package com.mjf.mashtun.backend.controllers;

import com.mjf.mashtun.backend.dtos.UnitDTO;
import com.mjf.mashtun.backend.services.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class UnitController {

    UnitService unitService;

    @GetMapping("/unit")
    public List<UnitDTO> getAllUnits(){
        return unitService.getUnits();
    }

    @GetMapping("/unit/{id}")
    public UnitDTO getUnit(@PathVariable long id){
        return unitService.getUnitsById(id);
    }

    @PostMapping("/unit")
    public ResponseEntity<String> createUnit(@RequestBody List<UnitDTO> unitDTOs){
        unitService.createUnits(unitDTOs);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/unit/{id}")
    public ResponseEntity<UnitDTO> updateUnit(@RequestBody UnitDTO unitDTO, @PathVariable long id){
        UnitDTO unitToUpdate = unitService.getUnitsById(id);
        if(unitToUpdate != null){
            unitToUpdate.setUnit_label(unitDTO.getUnit_label());
            unitService.updateUnit(unitToUpdate);
            return ResponseEntity.ok(unitToUpdate);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/unit/{id}")
    public int deleteUnit(@PathVariable long id){
        return unitService.deleteUnit(id);
    }

}
