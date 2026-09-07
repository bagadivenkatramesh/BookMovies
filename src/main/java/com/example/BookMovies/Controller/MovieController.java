package com.example.BookMovies.Controller;

import com.example.BookMovies.DTO.MovieDTO;
import com.example.BookMovies.Entity.Movie;
import com.example.BookMovies.Service.MovieService;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add_movie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDto){
        return ResponseEntity.ok(movieService.addMovie(movieDto));
    }

    @GetMapping("/get_all_movies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/get_movies_by_genre")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@RequestParam String genre){
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }

    @GetMapping("/get_movies_by_language")
    public ResponseEntity<List<Movie>> getMoviesByLanguage(@RequestParam String language){
        return ResponseEntity.ok(movieService.getMoviesByLanguage(language));
    }

    @GetMapping("/get_movie_by_title")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam String title){
        return ResponseEntity.ok(movieService.getMovieByTitle(title));
    }

    @PutMapping("/update_movie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDto){
        return ResponseEntity.ok(movieService.updateMovie(id,movieDto));
    }

    @DeleteMapping("/delete_movie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
