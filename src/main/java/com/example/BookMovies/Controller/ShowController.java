package com.example.BookMovies.Controller;

import com.example.BookMovies.DTO.ShowDTO;
import com.example.BookMovies.DTO.ShowResponseDTO;
import com.example.BookMovies.Entity.Show;
import com.example.BookMovies.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/create_show")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowResponseDTO> createShow(@RequestBody ShowDTO showDto){
        return ResponseEntity.ok(showService.createShow(showDto));
    }

    @PutMapping("/update_show/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDto){
        return ResponseEntity.ok(showService.updateShow(id, showDto));
    }

    @DeleteMapping("/delete_show/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get_all_shows")
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/get_shows_by_movie/{movieId}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/get_shows_by_theater/{theaterId}")
    public ResponseEntity<List<Show>> getShowsByTheater(@PathVariable Long theaterId){
        return ResponseEntity.ok(showService.getShowsByTheater(theaterId));
    }

}
