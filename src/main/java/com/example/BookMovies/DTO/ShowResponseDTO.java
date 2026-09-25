package com.example.BookMovies.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ShowResponseDTO {
    private Long id;
    private LocalDateTime time;
    private Double price;
    private Long movieId;
    private Long theaterId;
}

//purpose of this line - to maintain daily github commit activity, LOL