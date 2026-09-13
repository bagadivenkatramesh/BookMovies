package com.example.BookMovies.DTO;

import lombok.Data;

import java.util.List;

@Data
public class BookingDTO {

    private Integer numberOfSeats;

    private List<String> seatNumbers;

    private Long showId;
}
