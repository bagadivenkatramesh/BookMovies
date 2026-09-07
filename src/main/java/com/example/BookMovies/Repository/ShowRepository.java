package com.example.BookMovies.Repository;

import com.example.BookMovies.Entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    public List<Show> findByMovieId(Long movieId);
    public List<Show> findByTheaterId(Long theaterId);
}
