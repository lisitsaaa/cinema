package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CinemaService implements AbstractService<Cinema> {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public Cinema save(Cinema cinema) {
        Optional<Cinema> byCityAndName = cinemaRepository.findByCityAndName(cinema.getCity(), cinema.getName());
        if (byCityAndName.isPresent()) {
            throw new RuntimeException("cinema's already had");
        }
        return cinemaRepository.save(cinema);
    }

    @Override
    public void remove(long id) {
        cinemaRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cinema findById(long id) {
        Optional<Cinema> byId = cinemaRepository.findById(id);
        if (byId.isPresent()) {
            Cinema cinema = byId.get();
            cinema.getHalls().forEach(hall -> hall.getSeats()
                    .sort(Comparator.comparingInt(Seat::getRow)
                            .thenComparing(Seat::getSeat)));
            return cinema;
        }
        throw new NotFoundException(String.format("Cinema with id = %s not found", id));
    }

    public void update(Cinema cinema) {
        cinemaRepository.update(cinema.getId(), cinema.getName());
    }

    @Transactional(readOnly = true)
    public List<Cinema> findByCity(String cityName){
        List<Cinema> cinemas = cinemaRepository.findByCity(cityName);
        if (cinemas.isEmpty()) {
            throw new NotFoundException(String.format("Cinemas from city - %s not found", cityName));
        }
        return cinemas;
    }

    @Transactional(readOnly = true)
    public Cinema findByName(String name){
        Optional<Cinema> byName = cinemaRepository.findByName(name);
        if (byName.isPresent()) {
            return byName.get();
        }
        throw new NotFoundException(String.format("Cinema with name - %s not found", name));
    }
}
