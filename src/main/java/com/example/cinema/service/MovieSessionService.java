package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.exception.NotFoundException;
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
        movieSessionRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MovieSession findById(long id){
        Optional<MovieSession> byId = movieSessionRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new NotFoundException(String.format("MovieSession with id = %s not found", id));
    }

    public void update(MovieSession movieSession) {
        movieSessionRepository.update(movieSession.getId(), movieSession.getDate(),
                movieSession.getStartedTime(), movieSession.getPrice(), movieSession.getHall());
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findAllByDate(LocalDate date){
        List<MovieSession> movieSessions = movieSessionRepository.findByDate(date);
        if (movieSessions.isEmpty()) {
            throw new NotFoundException(String.format("MovieSessions with date = %s not found", date));
        }
        return movieSessions;
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findAllByCinema(Cinema cinema){
        List<MovieSession> movieSessions = movieSessionRepository.findByCinema(cinema);
        if (movieSessions.isEmpty()) {
            throw new NotFoundException(String.format("MovieSessions with cinema's name - %s not found", cinema.getName()));
        }
        return movieSessions;
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findAllByMovie(Movie movie){
        List<MovieSession> movieSessions = movieSessionRepository.findByMovie(movie);
        if (movieSessions.isEmpty()) {
            throw new NotFoundException(String.format("MovieSessions with movie's name - %s not found", movie.getName()));
        }
        return movieSessions;
    }
}
