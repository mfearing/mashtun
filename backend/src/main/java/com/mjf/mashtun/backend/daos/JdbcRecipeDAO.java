package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.RecipeDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
            throw new AppException("Error in Recipe.findAll()", HttpStatus.BAD_REQUEST);
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
            throw new AppException("Error in Recipe.findById()", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public RecipeDTO findByName(String name) {
        String sql = "select * from recipe where name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        try{
            return jdbcTemplate.queryForObject(sql, params, new RecipeDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Error in Recipe.findByName()", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {
        String sql = "insert into recipe (name, description, instructions) values (:name, :description, :instructions)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", recipeDTO.getName());
        params.addValue("description", recipeDTO.getDescription());
        params.addValue("instructions", recipeDTO.getInstructions());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try{
            jdbcTemplate.update(sql, params, keyHolder);
            long newId = Long.parseLong(String.valueOf(keyHolder.getKeyList().get(0).get("id")));
            recipeDTO.setId(newId);
            return recipeDTO;
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Error creating recipe, check logs.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public int update(RecipeDTO recipeDTO) {
        String sql = "update recipe set name = :name, description = :description, instructions = :instructions where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", recipeDTO.getName());
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
            recipeDTO.setName(rs.getString("name"));
            recipeDTO.setDescription(rs.getString("description"));
            recipeDTO.setInstructions(rs.getString("instructions"));

            return recipeDTO;
        }
    }

}
