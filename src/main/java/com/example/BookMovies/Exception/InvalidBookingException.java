package com.example.BookMovies.Exception;

public class InvalidBookingException extends RuntimeException {

    public InvalidBookingException(String message) {
        super(message);
    }
}