package com.example.BookMovies.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private Integer seatCapacity;
    private String screenType;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY)
    private List<Show> shows;
}
