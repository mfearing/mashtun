package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.RecipeIngredientDTO;
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
public class JdbcRecipeIngredientDAO implements RecipeIngredientDAO {
    private final Logger logger = LoggerFactory.getLogger(JdbcRecipeIngredientDAO.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcRecipeIngredientDAO(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RecipeIngredientDTO> findByRecipeId(long id) {
        String sql =
                "select ri.id, r.id as recipe_id, r.name as recipe_name, i.id as ingredient_id, \n " +
                    "i.ingredient_label, u.id as unit_id, u.unit_label, ri.amount \n " +
                    "from recipe r \n " +
                    "join recipe_ingredient ri on r.id = ri.recipe_id \n " +
                    "join ingredient i on ri.ingredient_id = i.id \n " +
                    "join unit u on ri.unit_id = u.id \n " +
                    "where ri.recipe_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try{
            return jdbcTemplate.query(sql, params, new RecipeIngredientDTORowMapper());
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Unable to find recipe ingredients", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public int createRecipeIngredients(List<RecipeIngredientDTO> recipeIngredientDTOs) {
        String sql = "insert into recipe_ingredient (recipe_id, ingredient_id, unit_id, amount) values (:recipe_id, :ingredient_id, :unit_id, :amount)";

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[recipeIngredientDTOs.size()];

        for(int i = 0; i < recipeIngredientDTOs.size(); i++){
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("recipe_id", recipeIngredientDTOs.get(i).getRecipe_id());
            param.addValue("ingredient_id", recipeIngredientDTOs.get(i).getIngredient_id());
            param.addValue("unit_id", recipeIngredientDTOs.get(i).getUnit_id());
            param.addValue("amount", recipeIngredientDTOs.get(i).getAmount());
            batchParams[i] = param;
        }

        try{
            jdbcTemplate.batchUpdate(sql, batchParams);
            return 1;
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
            throw new AppException("Recipe Ingredient insert failed", HttpStatus.BAD_REQUEST);
        }
    }


    public static class RecipeIngredientDTORowMapper implements RowMapper<RecipeIngredientDTO> {

        @Override
        public RecipeIngredientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
            recipeIngredientDTO.setId(rs.getLong("id"));
            recipeIngredientDTO.setRecipe_id(rs.getLong("recipe_id"));
            recipeIngredientDTO.setRecipe_label(rs.getString("recipe_name"));
            recipeIngredientDTO.setIngredient_id(rs.getLong("ingredient_id"));
            recipeIngredientDTO.setIngredient_label(rs.getString("ingredient_label"));
            recipeIngredientDTO.setUnit_id(rs.getLong("unit_id"));
            recipeIngredientDTO.setUnit_label(rs.getString("unit_label"));
            recipeIngredientDTO.setAmount(rs.getFloat("amount"));
            return recipeIngredientDTO;
        }
    }
}
