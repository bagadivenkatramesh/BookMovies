package com.example.BookMovies.DTO;

import com.example.BookMovies.Entity.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private BookingStatus bookingStatus;
    private Double price;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;
}
