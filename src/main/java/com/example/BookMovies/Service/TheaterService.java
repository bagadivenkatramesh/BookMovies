package com.example.BookMovies.Service;

import com.example.BookMovies.DTO.TheaterDTO;
import com.example.BookMovies.Entity.Theater;
import com.example.BookMovies.Repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public List<TheaterDTO> getTheatersByLocation(String location){
        List<Theater> theaters = theaterRepository.findByLocation(location);
        return theaters.stream().map(theater ->
            new TheaterDTO(
                    theater.getId(),
                    theater.getName(),
                    theater.getLocation(),
                    theater.getSeatCapacity(),
                    theater.getScreenType()
            )
        ).toList();
    }

    public Theater addTheater(TheaterDTO theaterDto){
        Theater theater = new Theater();
        theater.setName(theaterDto.getName());
        theater.setLocation(theaterDto.getLocation());
        theater.setSeatCapacity(theaterDto.getSeatCapacity());
        theater.setScreenType(theaterDto.getScreenType());

        return theaterRepository.save(theater);
    }

    public Theater updateTheater(Long id, TheaterDTO theaterDto){
        Optional<Theater> theaterOptional = theaterRepository.findById(id);
        if(theaterOptional.isPresent()){
            Theater theater = theaterOptional.get();
            theater.setName(theaterDto.getName());
            theater.setLocation(theaterDto.getLocation());
            theater.setSeatCapacity(theaterDto.getSeatCapacity());
            theater.setScreenType(theaterDto.getScreenType());

            return theaterRepository.save(theater);
        }else{
            throw new RuntimeException("No such theater found");
        }
    }

    public void deleteTheater(Long id){
        theaterRepository.deleteById(id);
    }
}
