package com.mjf.mashtun.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Based on Sergio Lema's Spring Security + React:
 * https://github.com/serlesen/fullstack-jwt/tree/chapter_1/backend
 */
@Component
public class PasswordConfig {

    /*
      Pulled this out of the SecurityConfig to prevent a circular dependency from
      SecurityConfig -> UserAuthenticationProvider -> UserService and back to SecurityConfig
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
