package com.example.BookMovies.Repository;

import com.example.BookMovies.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    public List<Movie> findByGenre(String genre);
    public List<Movie> findByLanguage(String language);
    public Optional<Movie> findByName(String name);
}
