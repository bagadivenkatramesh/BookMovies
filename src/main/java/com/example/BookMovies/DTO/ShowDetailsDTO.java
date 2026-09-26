package com.example.BookMovies.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowDetailsDTO {
    private Long id;
    private LocalDateTime time;
    private Double price;
    private Long movieId;
    private String movieName;
    private String movieLanguage;
    private String movieGenre;
    private Long theaterId;
    private String theaterName;
    private String theaterLocation;
    private String theaterScreenType;
    private Integer theaterSeatCapacity;
}
