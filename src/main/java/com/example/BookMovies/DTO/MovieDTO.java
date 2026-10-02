package com.example.BookMovies.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MovieDTO {
    @NotNull
    @Positive(message = "Movie ID must be greater that 0.") //not needed in create movie part, but needed in update movie part
    private Long id;

    @NotBlank(message = "Movie name is required.")
    private String name;

    @NotBlank(message = "Movie description is required.")
    private String description;

    @NotBlank(message = "Movie genre is required.")
    private String genre;

    @NotBlank(message = "Movie language is required.")
    private String language;

    @NotNull(message = "Release date is required.")
    private LocalDate releaseDate;

    @NotNull(message = "Duration is required.")
    @Positive(message = "Duration must be greater than 0.")
    private Integer duration;
}