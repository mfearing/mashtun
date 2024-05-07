package com.mjf.mashtun.backend.controllers;

import com.mjf.mashtun.backend.config.UserAuthenticationProvider;
import com.mjf.mashtun.backend.dtos.CredentialsDTO;
import com.mjf.mashtun.backend.dtos.SignUpDTO;
import com.mjf.mashtun.backend.dtos.UserDTO;
import com.mjf.mashtun.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Based on Sergio Lema's Spring Security + React:
 * https://github.com/serlesen/fullstack-jwt/tree/chapter_1/backend
 */
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid CredentialsDTO credentialsDto)
    {
        UserDTO userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid SignUpDTO signUpDto)
    {
        UserDTO userDto = userService.register(signUpDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
    }


}
