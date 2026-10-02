package com.example.BookMovies.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @NotBlank(message = "username is required.")
    private String username;

    @NotBlank(message = "email is required.")
    @Email(message = "email must be valid.")
    private String email;

    @NotBlank(message = "password is required.")
    @Size(min = 6, max = 15, message = "password must contain between 6 and 15 characters.")
    private String password;
}
