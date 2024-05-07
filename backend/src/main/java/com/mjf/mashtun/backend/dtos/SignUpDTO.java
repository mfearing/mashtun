package com.mjf.mashtun.backend.dtos;

public record SignUpDTO(String firstName, String lastName, String email, String login, char[] password) { }
