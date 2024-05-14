package com.mjf.mashtun.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjf.mashtun.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    @JsonIgnore
    private String password;
    private String token;
    private Role role;

}
