package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcUserDAO implements UserDAO {
    private Logger logger = LoggerFactory.getLogger(JdbcUserDAO.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserDAO (NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserDTO> findAll(){
        String sql = "select * from app_user";

        //used if needing parameters
        MapSqlParameterSource params = new MapSqlParameterSource();

        //maps result set row to UserDto
        UserDtoRowMapper mapper = new UserDtoRowMapper();

        List<UserDTO> users = jdbcTemplate.query(sql, params, mapper);
        return users;
    }

    @Override
    public UserDTO findById(long id) {
        String sql = "select * from app_user where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        UserDtoRowMapper mapper = new UserDtoRowMapper();

        try{
            return jdbcTemplate.queryForObject(sql, params, mapper);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public UserDTO findByLogin(String login) {
        String sql = "select * from app_user where login = :login";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        UserDtoRowMapper mapper = new UserDtoRowMapper();

        try{
            return jdbcTemplate.queryForObject(sql, params, mapper);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public UserDTO create(UserDTO user) {
        String sql = "insert into app_user (first_name, last_name, email, login, password) "
                + " values(:first_name, :last_name, :email, :login, :password)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", user.getFirstName());
        params.addValue("last_name", user.getLastName());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("password", user.getPassword()); //should be Bycrypted already

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try{
            int rows = jdbcTemplate.update(sql, params, keyHolder);
            long newId = Long.parseLong(String.valueOf(keyHolder.getKeyList().get(0).get("id")));
            user.setId(newId);
            return user;
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return null;
    }

    @Override
    public int update(UserDTO user) {
        String sql = "update app_user set first_name = :first_name, last_name = :last_name, " +
                "email = :email, login = :login, password = :password " +
                "where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", user.getFirstName());
        params.addValue("last_name", user.getLastName());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("password", user.getPassword()); //should be Bycrypted already
        params.addValue("id", user.getId());

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return 0;
    }

    @Override
    public int deleteById(long id) {
        String sql = "delete from app_user where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        UserDtoRowMapper mapper = new UserDtoRowMapper();

        try{
            return jdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            logger.debug(e.getMessage());
        }

        return 0;
    }


    public class UserDtoRowMapper implements RowMapper<UserDTO> {

        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDTO result = new UserDTO();
            result.setId(rs.getLong("id"));
            result.setFirstName(rs.getString("first_name"));
            result.setLastName(rs.getString("last_name"));
            result.setEmail(rs.getString("email"));
            result.setLogin(rs.getString("login"));
            result.setPassword(rs.getString("password"));
            //no password on DTOs
            //no tokens stored in the database
            return result;
        }
    }


}
