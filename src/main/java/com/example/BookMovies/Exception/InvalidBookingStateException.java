package com.example.BookMovies.Exception;

public class InvalidBookingStateException extends RuntimeException {

    public InvalidBookingStateException(String message) {
        super(message);
    }
}