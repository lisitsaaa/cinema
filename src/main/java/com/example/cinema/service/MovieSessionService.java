package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.repository.MovieSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieSessionService implements AbstractService<MovieSession>{
    @Autowired
    private MovieSessionRepository movieSessionRepository;

    @Override
    public MovieSession save(MovieSession movieSession){
        return movieSessionRepository.save(movieSession);
    }

    @Override
    public void remove(long id) {
        movieSessionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieSession findById(long id){
        Optional<MovieSession> byId = movieSessionRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new RuntimeException("incorrect id");
    }

    @Override
    public void update(MovieSession movieSession) {
//        movieSessionRepository.update(movieSession.getId(),
//                movieSession.getDate(), movieSession.getStartedTime(),
//                movieSession.getPrice(), movieSession.getHall());
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findByDate(LocalDate date){
        return movieSessionRepository.findByDate(date);
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findByCinema(Cinema cinema){
        return movieSessionRepository.findByCinema(cinema);
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findByMovie(Movie movie){
        return movieSessionRepository.findByMovie(movie);
    }
}
