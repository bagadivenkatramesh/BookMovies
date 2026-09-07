package com.example.BookMovies.Controller;


import com.example.BookMovies.DTO.TheaterDTO;
import com.example.BookMovies.Entity.Theater;
import com.example.BookMovies.Service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @GetMapping("/get_theaters_by_location")
    public ResponseEntity<List<Theater>> getTheatersByLocation(@RequestParam String location){
        return ResponseEntity.ok(theaterService.getTheatersByLocation(location));
    }

    @PostMapping("/add_theater")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterDTO theaterDto){
        return ResponseEntity.ok(theaterService.addTheater(theaterDto));
    }

    @PutMapping("/update_theater/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> updateTheater(@PathVariable Long id, @RequestBody TheaterDTO theaterDto){
        return ResponseEntity.ok(theaterService.updateTheater(id, theaterDto));
    }

    @DeleteMapping("/delete_theater/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id){
        theaterService.deleteTheater(id);
        return ResponseEntity.ok().build();
    }
}
