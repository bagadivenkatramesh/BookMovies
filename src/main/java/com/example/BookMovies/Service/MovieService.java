package com.example.BookMovies.Service;

import com.example.BookMovies.DTO.MovieDTO;
import com.example.BookMovies.Entity.Movie;
import com.example.BookMovies.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDTO movieDto){
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setLanguage(movieDto.getLanguage());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setDuration(movieDto.getDuration());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre){
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByLanguage(String language){
        return movieRepository.findByLanguage(language);
    }

    public Movie getMovieByTitle(String name){
        Optional<Movie> movieOptional = movieRepository.findByName(name);
        if(movieOptional.isPresent()){
            return movieOptional.get();
        }else{
            throw new RuntimeException("No movie found for the name "+name);
        }
    }

    public Movie updateMovie(Long id, MovieDTO movieDto){
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if(movieOptional.isPresent()){
            Movie movie = movieOptional.get();
            movie.setName(movieDto.getName());
            movie.setDescription(movieDto.getDescription());
            movie.setGenre(movieDto.getGenre());
            movie.setLanguage(movieDto.getLanguage());
            movie.setReleaseDate(movieDto.getReleaseDate());
            movie.setDuration(movieDto.getDuration());
            return movieRepository.save(movie);
        }else{
            throw new RuntimeException("No such movie found");
        }
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }
}
