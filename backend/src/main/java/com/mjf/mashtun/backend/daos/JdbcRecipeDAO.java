package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.RecipeDTO;
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
public class JdbcRecipeDAO implements RecipeDAO {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRecipeDAO.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcRecipeDAO(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RecipeDTO> findAll() {
        String sql = "select * from recipe";
        try{
            return jdbcTemplate.query(sql, new MapSqlParameterSource(), new RecipeDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public RecipeDTO findById(long id) {
        String sql = "select * from recipe where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.queryForObject(sql, params, new RecipeDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public RecipeDTO findByRecipeLabel(String recipe_label) {
        String sql = "select * from recipe where recipe_label = :recipe_label";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("recipe_label", recipe_label);

        try{
            return jdbcTemplate.queryForObject(sql, params, new RecipeDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void create(List<RecipeDTO> recipeDTOs) {
        String sql = "insert into recipe (recipe_label, description, instructions) values (:recipe_label, :description, :instructions)";
        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[recipeDTOs.size()];

        for(int i = 0; i < recipeDTOs.size(); i++) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("recipe_label", recipeDTOs.get(i).getRecipe_label());
            param.addValue("description", recipeDTOs.get(i).getDescription());
            param.addValue("instructions", recipeDTOs.get(i).getInstructions());
            batchParams[i] = param;
        }

        try{
            jdbcTemplate.batchUpdate(sql, batchParams);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Error creating recipe, check logs.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public int update(RecipeDTO recipeDTO) {
        String sql = "update recipe set recipe_label = :recipe_label, description = :description, instructions = :instructions where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("recipe_label", recipeDTO.getRecipe_label());
        params.addValue("description", recipeDTO.getDescription());
        params.addValue("instructions", recipeDTO.getInstructions());
        params.addValue("id", recipeDTO.getId());

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Error updating recipe, check logs", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public int delete(long id) {
        String sql = "delete from recipe where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Error deleting recipe, check logs", HttpStatus.BAD_REQUEST);
        }
    }


    public static class RecipeDTORowMapper implements RowMapper<RecipeDTO>{

        @Override
        public RecipeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeDTO recipeDTO = new RecipeDTO();

            recipeDTO.setId(rs.getLong("id"));
            recipeDTO.setRecipe_label(rs.getString("recipe_label"));
            recipeDTO.setDescription(rs.getString("description"));
            recipeDTO.setInstructions(rs.getString("instructions"));

            return recipeDTO;
        }
    }

}
