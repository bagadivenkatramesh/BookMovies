package com.example.BookMovies.Controller;

import com.example.BookMovies.DTO.BookingDTO;
import com.example.BookMovies.Entity.Booking;
import com.example.BookMovies.Entity.BookingStatus;
import com.example.BookMovies.Entity.User;
import com.example.BookMovies.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create_booking")
    public ResponseEntity<Booking> createBooking(
            @RequestBody BookingDTO bookingDto,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                bookingService.createBooking(bookingDto, user)
        );
    }

    @GetMapping("/my_bookings")
    public ResponseEntity<List<Booking>> getMyBookings(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                bookingService.getBookingsForUser(user.getId())
        );
    }

    @GetMapping("/get_bookings_for_show/{show_id}")
    public ResponseEntity<List<Booking>> getBookingsForShow(@PathVariable Long show_id){
        return ResponseEntity.ok(bookingService.getBookingsForShow(show_id));
    }

    @PutMapping("/confirm_booking/{booking_id}")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long booking_id){
        return ResponseEntity.ok(bookingService.confirmBooking(booking_id));
    }

    @PutMapping("/cancel_booking/{booking_id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long booking_id){
        return ResponseEntity.ok(bookingService.cancelBooking(booking_id));
    }

    @GetMapping("/get_bookings_by_status/{booking_status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus booking_status){
        return ResponseEntity.ok(bookingService.getBookingsByStatus(booking_status));
    }
}
