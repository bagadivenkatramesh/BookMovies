package com.example.BookMovies.Repository;

import com.example.BookMovies.Entity.Booking;
import com.example.BookMovies.Entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public List<Booking> findByUserId(Long userId);
    public List<Booking> findByShowId(Long showId);
    public List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
