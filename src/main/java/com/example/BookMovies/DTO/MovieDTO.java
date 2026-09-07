package com.example.BookMovies.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieDTO {
    private String name;
    private String description;
    private String genre;
    private String language;
    private LocalDate releaseDate;
    private Integer duration;
}
