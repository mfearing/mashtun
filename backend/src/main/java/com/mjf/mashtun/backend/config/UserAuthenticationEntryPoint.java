package com.mjf.mashtun.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjf.mashtun.backend.dtos.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Based on Sergio Lema's Spring Security + React:
 * https://github.com/serlesen/fullstack-jwt/tree/chapter_1/backend
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        OBJECT_MAPPER.writeValue(response.getOutputStream(), new ErrorDTO("Unauthorized path"));
    }

}
