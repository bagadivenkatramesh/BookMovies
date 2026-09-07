package com.example.BookMovies.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String genre;
    private String language;
    private LocalDate releaseDate;
    private Integer duration;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Show> shows;
}
