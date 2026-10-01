package com.example.BookMovies.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private Integer statusCode;
    private String errorMessage;
    private LocalDateTime timestamp;
}
