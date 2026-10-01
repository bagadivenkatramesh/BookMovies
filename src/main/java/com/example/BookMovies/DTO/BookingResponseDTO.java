package com.example.BookMovies.DTO;

import com.example.BookMovies.Entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private BookingStatus bookingStatus;
    private Double price;
    private List<String> seatNumbers;
    private Long showId;
    private Long userId;
    private LocalDateTime showTime;
    private String movieName;
    private Long movieId;
    private String theaterName;
    private String theaterLocation;
    private String theaterScreenType;
}
