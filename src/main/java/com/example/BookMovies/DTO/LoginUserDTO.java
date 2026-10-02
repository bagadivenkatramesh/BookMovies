package com.example.BookMovies.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDTO {
    @NotBlank(message = "username is required.")
    private String username;

    @NotBlank(message = "password is required.")
    @Size(min = 6, max = 15, message = "password must be between 6 and 15 characters.")
    private String password;
}
