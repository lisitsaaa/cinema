package com.example.cinema.service;

import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.repository.MovieSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MovieSessionService {
    @Autowired
    private MovieSessionRepository movieSessionRepository;

    public MovieSession save(MovieSession movieSession){
        return movieSessionRepository.save(movieSession);
    }

    @Transactional(readOnly = true)
    public MovieSession findById(long id){
        Optional<MovieSession> byId = movieSessionRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new RuntimeException("incorrect id");
    }
}
