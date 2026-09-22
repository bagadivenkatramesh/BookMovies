package com.example.BookMovies.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowResponseDTO {
    private Long id;
    private LocalDateTime time;
    private Double price;
    private Long movieId;
    private Long theaterId;
}