package com.example.BookMovies.DTO;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterUserDTO {
    private String username;
    private String email;
    private String password;
}
