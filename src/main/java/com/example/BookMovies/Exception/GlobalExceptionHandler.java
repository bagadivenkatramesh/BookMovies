package com.example.BookMovies.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicateResource(
            DuplicateResourceException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiError> handleInvalidOperation(
            InvalidOperationException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    // NEW
    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ApiError> handleInvalidBooking(
            InvalidBookingException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    // NEW
    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<ApiError> handleSeatUnavailable(
            SeatUnavailableException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    // NEW
    @ExceptionHandler(InvalidBookingStateException.class)
    public ResponseEntity<ApiError> handleInvalidBookingState(
            InvalidBookingStateException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }
}