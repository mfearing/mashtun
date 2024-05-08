package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.IngredientDTO;
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
public class JdbcIngredientDAO implements IngredientDAO {

    private final Logger logger = LoggerFactory.getLogger(JdbcIngredientDAO.class);

    NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcIngredientDAO(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<IngredientDTO> findAll() {
        String sql = "select * from ingredient";
        try {
            return jdbcTemplate.query(sql, new MapSqlParameterSource(), new IngredientDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public List<IngredientDTO> findAllPaginated(int pageNumber, int pageSize) {
        int offset = (pageNumber -1) * pageSize;
        String sql = "select * from ingredient limit :pageSize offset :offset";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pageSize", pageSize);
        params.addValue("offset", offset);

        return jdbcTemplate.query(sql, params, new IngredientDTORowMapper());
    }


    @Override
    public IngredientDTO findById(long id) {
        String sql = "select * from ingredient where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.queryForObject(sql, params, new IngredientDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public IngredientDTO findByIngredientLabel(String label) {
        String sql = "select * from ingredient where ingredient_label = :ingredient_label";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ingredient_label", label);

        try{
            return jdbcTemplate.queryForObject(sql, params, new IngredientDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public void create(List<IngredientDTO> ingredientDTOs) {
        String sql = "insert into ingredient (ingredient_label) values (:ingredient_label)";

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[ingredientDTOs.size()];

        for(int i = 0; i < ingredientDTOs.size(); i++){
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("ingredient_label", ingredientDTOs.get(i).getIngredient_label());
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
    public int update(IngredientDTO ingredientDTO) {
        String sql = "update ingredient set ingredient_label = :ingredient_label where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ingredient_label", ingredientDTO.getIngredient_label());
        params.addValue("id", ingredientDTO.getId());

        try{
            int rows = jdbcTemplate.update(sql, params);
            return rows;
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return 0;
    }

    @Override
    public int delete(long id) {
        String sql = "delete from ingredient where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return 0;
    }

    public static class IngredientDTORowMapper implements RowMapper<IngredientDTO> {

        @Override
        public IngredientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IngredientDTO result = new IngredientDTO();
            result.setId(rs.getLong("id"));
            result.setIngredient_label(rs.getString("ingredient_label"));
            return result;
        }
    }


}
