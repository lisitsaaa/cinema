package com.example.cinema.service;

import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieService implements AbstractService<Movie>{
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie save(Movie movie){
        return movieRepository.save(movie);
    }

    @Override
    public void remove(long id) {
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return movie.get();
        }
        throw new RuntimeException("incorrect id");
    }

    @Override
    public void update(Movie movie) {
//        movieRepository.update(movie.getId(), movie.getName(),
//                movie.getImage(), movie.getDuration(),
//                movie.getReleaseYear(), movie.getDescription(),
//                movie.getAgeLimit(), movie.getGenres(), movie.getType());
    }

    @Transactional(readOnly = true)
    public List<Movie> findAll(){
        return movieRepository.findAll();
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
