package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.UnitDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcUnitDAO implements UnitDAO {

    private final Logger logger = LoggerFactory.getLogger(JdbcUnitDAO.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUnitDAO(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<UnitDTO> findAll() {
        String sql = "select * from unit";
        try {
            return jdbcTemplate.query(sql, new MapSqlParameterSource(), new UnitDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public UnitDTO findById(long id) {
        String sql = "select * from unit where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.queryForObject(sql, params, new UnitDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public UnitDTO findByUnitLabel(String label) {
        String sql = "select * from unit where unit_label = :unit_label";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("unit_label", label);

        try{
            return jdbcTemplate.queryForObject(sql, params, new UnitDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public void create(List<UnitDTO> unitDTOs) {
        String sql = "insert into unit (unit_label) values (:unit_label)";

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[unitDTOs.size()];

        for(int i = 0; i < unitDTOs.size(); i++){
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("unit_label", unitDTOs.get(i).getUnit_label());
            batchParams[i] = param;
        }

        try{
            jdbcTemplate.batchUpdate(sql, batchParams);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public int update(UnitDTO unitDTO) {
        String sql = "update unit set unit_label = :unit_label where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("unit_label", unitDTO.getUnit_label());
        params.addValue("id", unitDTO.getId());

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return 0;
    }

    @Override
    public int delete(long id) {
        String sql = "delete from unit where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return 0;
    }


    public static class UnitDTORowMapper implements RowMapper<UnitDTO>{

        @Override
        public UnitDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UnitDTO result = new UnitDTO();
            result.setId(rs.getLong("id"));
            result.setUnit_label(rs.getString("unit_label"));
            return result;
        }
    }

}
