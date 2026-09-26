package com.example.BookMovies.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowWithTheaterOrMovieDTO {
    private Long id;
    private LocalDateTime time;
    private Double price;
    private Long movieId;
    private Long theaterId;
    private String theaterOrMovieName;
    private String theaterLocationOrMovieLanguage;
    private String theaterScreenTypeOrMovieGenre;
}
