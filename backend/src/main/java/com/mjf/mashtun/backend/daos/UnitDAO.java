package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.UnitDTO;

import java.util.List;

public interface UnitDAO {
    List<UnitDTO> findAll();
    UnitDTO findById(long id);
    UnitDTO findByUnitLabel(String label);
    UnitDTO create(UnitDTO unitDTO);
    int update(UnitDTO unitDTO);
    int delete(long id);
}
