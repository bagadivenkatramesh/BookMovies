package com.example.BookMovies.Service;

import com.example.BookMovies.DTO.BookingDTO;
import com.example.BookMovies.DTO.BookingResponseDTO;
import com.example.BookMovies.Entity.*;
import com.example.BookMovies.Exception.InvalidBookingException;       // CHANGED
import com.example.BookMovies.Exception.InvalidBookingStateException;  // CHANGED
import com.example.BookMovies.Exception.ResourceNotFoundException;
import com.example.BookMovies.Exception.SeatUnavailableException;      // CHANGED
import com.example.BookMovies.Repository.BookingRepository;
import com.example.BookMovies.Repository.ShowRepository;
import com.example.BookMovies.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    public void createBooking(BookingDTO bookingDto, User _user) {

        // Validation for seatNumbers list size and numberOfSeats equality
        if (bookingDto.getSeatNumbers().size() != bookingDto.getNumberOfSeats()) {

            throw new InvalidBookingException(
                    "There is a mismatch in the number of seats and seat list size"
            );
        }

        //check for no duplicate seats in the incoming request
        if (new HashSet<>(bookingDto.getSeatNumbers()).size()
                != bookingDto.getSeatNumbers().size()) {

            throw new InvalidBookingException(
                    "Duplicate seat numbers in booking request"
            );
        }

        // show availability check
        Show show = showRepository.findById(bookingDto.getShowId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No show exists with id " + bookingDto.getShowId()
                        )
                );
        //TO DO - a check to ensure that the incoming request doesn't contains seats which doesn't exist in the theater
        //for example. let's say the theater has seats A1 to A10. incoming request with a seat B5 should not pass
        //this requires modifications in the Theater entity and introduction of a new @ElementCollection in the Theater entity

        //seat availability check using number of seats
        int totalCapacity = show.getTheater().getSeatCapacity();

        int bookedSeats = show.getBookings().stream()
                .filter(booking ->
                        booking.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        if (totalCapacity - bookedSeats < bookingDto.getNumberOfSeats()) {

            throw new SeatUnavailableException(
                    "Not enough seats are available"
            );
        }

        // Duplicate seats check
        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(booking ->
                        booking.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(booking ->
                        booking.getSeatNumbers().stream())
                .collect(Collectors.toSet());

        List<String> duplicateSeats = bookingDto.getSeatNumbers().stream()
                .filter(occupiedSeats::contains)
                .toList();

        if (!duplicateSeats.isEmpty()) {

            throw new SeatUnavailableException(
                    "Seats are already booked: " + duplicateSeats
            );
        }

        Booking booking = new Booking();

        booking.setNumberOfSeats(bookingDto.getNumberOfSeats());
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPrice(
                calculateTotalAmount(
                        show.getPrice(),
                        bookingDto.getNumberOfSeats()
                )
        );
        booking.setSeatNumbers(bookingDto.getSeatNumbers());
        booking.setUser(_user);
        booking.setShow(show);

        bookingRepository.save(booking);
    }

    public List<BookingResponseDTO> getBookingsForUser(Long userId) {

        List<Booking> bookings =
                bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getId(),
                        booking.getNumberOfSeats(),
                        booking.getBookingTime(),
                        booking.getBookingStatus(),
                        booking.getPrice(),
                        booking.getSeatNumbers(),
                        booking.getShow().getId(),
                        booking.getUser().getId(),
                        booking.getShow().getTime(),
                        booking.getShow().getMovie().getName(),
                        booking.getShow().getMovie().getId(),
                        booking.getShow().getTheater().getName(),
                        booking.getShow().getTheater().getLocation(),
                        booking.getShow().getTheater().getScreenType()
                ))
                .toList();
    }

    public List<BookingResponseDTO> getBookingsForShow(Long showId) {

        List<Booking> bookings =
                bookingRepository.findByShowId(showId);

        return bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getId(),
                        booking.getNumberOfSeats(),
                        booking.getBookingTime(),
                        booking.getBookingStatus(),
                        booking.getPrice(),
                        booking.getSeatNumbers(),
                        booking.getShow().getId(),
                        booking.getUser().getId(),
                        booking.getShow().getTime(),
                        booking.getShow().getMovie().getName(),
                        booking.getShow().getMovie().getId(),
                        booking.getShow().getTheater().getName(),
                        booking.getShow().getTheater().getLocation(),
                        booking.getShow().getTheater().getScreenType()
                ))
                .toList();
    }

    public Booking confirmBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No booking found with the booking id " + bookingId
                        )
                );

        if (booking.getBookingStatus() != BookingStatus.PENDING) {

            // CHANGED
            throw new InvalidBookingStateException(
                    "Booking is not in pending state"
            );
        }

        // Payment process

        booking.setBookingStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No booking found with the booking id " + bookingId
                        )
                );

        validateCancellation(booking);

        booking.setBookingStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(booking);
    }

    public List<BookingResponseDTO> getBookingsByStatus(
            BookingStatus bookingStatus) {
        List<Booking> bookings = bookingRepository.findByBookingStatus(bookingStatus);
        return bookings.stream().map(booking -> new BookingResponseDTO(
                booking.getId(),
                booking.getNumberOfSeats(),
                booking.getBookingTime(),
                booking.getBookingStatus(),
                booking.getPrice(),
                booking.getSeatNumbers(),
                booking.getShow().getId(),
                booking.getUser().getId(),
                booking.getShow().getTime(),
                booking.getShow().getMovie().getName(),
                booking.getShow().getMovie().getId(),
                booking.getShow().getTheater().getName(),
                booking.getShow().getTheater().getLocation(),
                booking.getShow().getTheater().getScreenType()
        )).toList();
    }

    public void validateCancellation(Booking booking) {

        LocalDateTime showTime =
                booking.getShow().getTime();

        LocalDateTime deadlineTime =
                showTime.minusHours(2);

        if (LocalDateTime.now().isAfter(deadlineTime)) {

            // CHANGED
            throw new InvalidBookingStateException(
                    "Cannot cancel the booking within 2 hours of the show"
            );
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {

            // CHANGED
            throw new InvalidBookingStateException(
                    "Booking has already been cancelled"
            );
        }
    }

    public Double calculateTotalAmount(
            Double price,
            Integer numberOfSeats) {

        return price * numberOfSeats;
    }
}

//testing for railway's 'auto deploy when pushed to main branch in github' feature - disable