package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.UnitDTO;

import java.util.List;

public interface UnitDAO {
    List<UnitDTO> findAll();
    UnitDTO findById(long id);
    UnitDTO findByUnitLabel(String label);
    void create(List<UnitDTO> unitDTOs);
    int update(UnitDTO unitDTO);
    int delete(long id);
}
