package com.mjf.mashtun.backend.config;

import com.mjf.mashtun.backend.dtos.ErrorDTO;
import com.mjf.mashtun.backend.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Based on Sergio Lema's Spring Security + React:
 * https://github.com/serlesen/fullstack-jwt/tree/chapter_1/backend
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(AppException ex){
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorDTO(ex.getMessage()));
    }
}
