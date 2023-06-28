package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    public Cinema save(Cinema cinema){
        return cinemaRepository.save(cinema);
    }
}
