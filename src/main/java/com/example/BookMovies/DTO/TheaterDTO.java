package com.example.BookMovies.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TheaterDTO {
    private Long id;
    private String name;
    private String location;
    private Integer seatCapacity;
    private String screenType;
}
