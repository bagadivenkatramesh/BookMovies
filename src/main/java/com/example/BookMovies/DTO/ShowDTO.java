package com.example.BookMovies.DTO;

import com.example.BookMovies.Entity.Movie;
import com.example.BookMovies.Entity.Theater;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowDTO {
    private LocalDateTime time;
    private Double price;
    private Long movieId;
    private Long theaterId;
}
