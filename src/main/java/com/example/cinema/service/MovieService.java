package com.example.cinema.service;

import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie save(Movie movie){
        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public Movie findByName(String name){
        Optional<Movie> byName = movieRepository.findByName(name);
        if (byName.isPresent()) {
            return byName.get();
        }
        throw new RuntimeException("incorrect name");
    }
}
