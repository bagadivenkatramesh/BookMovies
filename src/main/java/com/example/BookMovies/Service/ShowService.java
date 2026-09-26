package com.example.BookMovies.Service;

import com.example.BookMovies.DTO.ShowDTO;
import com.example.BookMovies.DTO.ShowResponseDTO;
import com.example.BookMovies.DTO.ShowWithTheaterOrMovieDTO;
import com.example.BookMovies.Entity.Booking;
import com.example.BookMovies.Entity.Movie;
import com.example.BookMovies.Entity.Show;
import com.example.BookMovies.Entity.Theater;
import com.example.BookMovies.Repository.MovieRepository;
import com.example.BookMovies.Repository.ShowRepository;
import com.example.BookMovies.Repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public ShowResponseDTO createShow(ShowDTO showDto){
        //check if a show with the exact same start time exists in the theater
        if(showRepository.existsByTimeAndTheaterId(showDto.getTime(), showDto.getTheaterId())){
            throw new RuntimeException("A show already exists in this theater at this time");
        }
        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(()->new RuntimeException("No movie found with id "+showDto.getMovieId()));
        Theater theater = theaterRepository.findById(showDto.getTheaterId())
                .orElseThrow(()->new RuntimeException("No theatre found with id "+showDto.getTheaterId()));
        Show show = new Show();
        show.setTime(showDto.getTime());
        show.setPrice(showDto.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);
        Show savedShow = showRepository.save(show);
        return ShowResponseDTO.builder()
                .id(savedShow.getId())
                .time(savedShow.getTime())
                .price(savedShow.getPrice())
                .movieId(savedShow.getMovie().getId())
                .theaterId(savedShow.getTheater().getId())
                .build();
    }

    public Show updateShow(Long id, ShowDTO showDto){
        Show show = showRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No show found with id "+id));
        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(()->new RuntimeException("No movie found with id "+showDto.getMovieId()));
        Theater theater = theaterRepository.findById(showDto.getTheaterId())
                .orElseThrow(()->new RuntimeException("No theatre found with id "+showDto.getTheaterId()));
        show.setTime(showDto.getTime());
        show.setPrice(showDto.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    public void deleteShow(Long id){
        Show show = showRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No show found with id "+id));
        List<Booking> bookings = show.getBookings();
        if(bookings.isEmpty()){
            showRepository.delete(show);
        }else{
            throw new RuntimeException("Cannot delete this show as it has bookings");
        }
    }

    public List<Show> getAllShows(){
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId){
        return showRepository.findByMovieId(movieId);
    }

    public List<ShowWithTheaterOrMovieDTO> getShowsByTheater(Long theaterId){
        List<Show> shows = showRepository.findByTheaterId(theaterId);
        return shows.stream().map(show -> new ShowWithTheaterOrMovieDTO(
                show.getId(),
                show.getTime(),
                show.getPrice(),
                show.getMovie().getId(),
                show.getTheater().getId(),
                show.getMovie().getName(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre()
        )).toList();
    }
}
