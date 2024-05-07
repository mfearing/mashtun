package com.mjf.mashtun.backend.daos;

import com.mjf.mashtun.backend.dtos.UserDTO;

import java.util.List;

public interface UserDAO {

    List<UserDTO> findAll();
    UserDTO findById(long id);
    UserDTO findByLogin(String login);
    UserDTO create(UserDTO user);
    int update(UserDTO user);
    int deleteById(long id);
}
