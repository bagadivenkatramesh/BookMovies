package com.example.BookMovies.Repository;

import com.example.BookMovies.Entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    public List<Theater> findByLocation(String location);
}
