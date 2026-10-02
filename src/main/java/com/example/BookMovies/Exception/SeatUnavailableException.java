package com.example.BookMovies.Exception;

public class SeatUnavailableException extends RuntimeException {

    public SeatUnavailableException(String message) {
        super(message);
    }
}