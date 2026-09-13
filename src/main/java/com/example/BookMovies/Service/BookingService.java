package com.example.BookMovies.Service;

import com.example.BookMovies.DTO.BookingDTO;
import com.example.BookMovies.Entity.*;
import com.example.BookMovies.Repository.BookingRepository;
import com.example.BookMovies.Repository.ShowRepository;
import com.example.BookMovies.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingDTO bookingDto, User user){
        //validation for seatNumbers list size and numberOfSeats equality
        if(bookingDto.getSeatNumbers().size()!=bookingDto.getNumberOfSeats()){
            throw new RuntimeException("There is a mismatch in the number of seats and seat list size");
        }

        //seat availability check
        Show show = showRepository.findById(bookingDto.getShowId())
                .orElseThrow(()->new RuntimeException("No show exists with id "+bookingDto.getShowId()));
        int totalCapacity = show.getTheater().getSeatCapacity();
        int bookedSeats = show.getBookings().stream()
                              .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                              .mapToInt(Booking::getNumberOfSeats)
                              .sum();
        if(totalCapacity-bookedSeats < bookingDto.getNumberOfSeats()){
            throw new RuntimeException("Not enough seats are available");
        }

        //duplicate seats check
        Set<String> occupiedSeats = show.getBookings().stream()
                                        .filter(b->b.getBookingStatus()!=BookingStatus.CANCELLED)
                                        .flatMap(b->b.getSeatNumbers().stream())
                                        .collect(Collectors.toSet());
        List<String> duplicateSeats = bookingDto.getSeatNumbers().stream()
                                                .filter(occupiedSeats::contains)
                                                .toList();
        if(!duplicateSeats.isEmpty()){
            throw new RuntimeException("Seats are already booked");
        }

        User user = userRepository.findById(bookingDto.getUserId()).orElseThrow(()->new RuntimeException("No user found with the id "+bookingDto.getUserId()));
        Booking booking = new Booking();
        booking.setNumberOfSeats(bookingDto.getNumberOfSeats());
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPrice(calculateTotalAmount(show.getPrice(), bookingDto.getNumberOfSeats()));
        booking.setSeatNumbers(bookingDto.getSeatNumbers());
        booking.setUser(user);
        booking.setShow(show);

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForUser(Long userId){
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getBookingsForShow(Long showId){
        return bookingRepository.findByShowId(showId);
    }

    public Booking confirmBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("No booking found with the booking id "+bookingId));
        if(booking.getBookingStatus()!=BookingStatus.PENDING){
            throw new RuntimeException("Booking is not in pending state");
        }
        //payment process
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("No booking found with the booking id "+bookingId));
        validateCancellation(booking);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByStatus(BookingStatus bookingStatus){
        return bookingRepository.findByBookingStatus(bookingStatus);
    }

    public void validateCancellation(Booking booking){
        LocalDateTime showTime = booking.getShow().getTime();
        LocalDateTime deadlineTime = showTime.minusHours(2);

        if(LocalDateTime.now().isAfter(deadlineTime)){
            throw new RuntimeException("Cannot cancel the booking");
        }

        if(booking.getBookingStatus()==BookingStatus.CANCELLED){
            throw new RuntimeException("Booking has already been cancelled");
        }
    }

    public Double calculateTotalAmount(Double price, Integer numberOfSeats){
        return price*numberOfSeats;
    }
}
