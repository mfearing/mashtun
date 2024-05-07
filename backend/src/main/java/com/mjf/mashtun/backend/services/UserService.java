package com.mjf.mashtun.backend.services;

import com.mjf.mashtun.backend.daos.UserDAO;
import com.mjf.mashtun.backend.dtos.CredentialsDTO;
import com.mjf.mashtun.backend.dtos.SignUpDTO;
import com.mjf.mashtun.backend.dtos.UserDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    public UserDTO login(CredentialsDTO credentialsDto){

        UserDTO userDto = userDAO.findByLogin(credentialsDto.login());
        if(userDto == null){
            throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
        }

        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), userDto.getPassword())){
            return userDto;
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public UserDTO register(SignUpDTO signUpDto){

        UserDTO userDTO = userDAO.findByLogin(signUpDto.login());
        if(userDTO != null) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        userDTO = new UserDTO();
        userDTO.setFirstName(signUpDto.firstName());
        userDTO.setLastName(signUpDto.lastName());
        userDTO.setEmail(signUpDto.email());
        userDTO.setLogin(signUpDto.login());
        userDTO.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        if(userDAO.create(userDTO) != null){
            return userDTO;
        } else {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public UserDTO findByLogin(String login){
        return userDAO.findByLogin(login);
    }

}
