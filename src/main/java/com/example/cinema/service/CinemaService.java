package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
@Transactional
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    public Cinema save(Cinema cinema){
        return cinemaRepository.save(cinema);
    }


    public Cinema findById(long id){
        Optional<Cinema> byId = cinemaRepository.findById(id);
        if (byId.isPresent()) {
            Cinema cinema = byId.get();
            cinema.getHalls().forEach(hall -> hall.getSeats()
                    .sort(Comparator.comparingInt(Seat::getRow)
                            .thenComparing(Seat::getSeat)));
            return cinema;
        }
        throw new RuntimeException("incorrect id");
    }
}
