package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.MovieSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class MovieSessionService implements AbstractService<MovieSession> {
    @Autowired
    private MovieSessionRepository movieSessionRepository;

    private final static Logger logger = Logger.getLogger(CinemaService.class.getName());

    @Override
    public MovieSession save(MovieSession movieSession) {
        logger.info("saving is started");
        if (movieSessionRepository.findByDateAndStartedTimeAndHall(movieSession.getDate(),
                        movieSession.getStartedTime(),
                        movieSession.getHall())
                .isPresent()) {
            logger.info("already existed");
            throw new ExistsException(String.format("Hall - %s in this date - %s and time %s already existed", movieSession.getHall().getName(),
                    movieSession.getDate(),
                    movieSession.getStartedTime()));
        }
        logger.info("movie session was successfully saved");
        return movieSessionRepository.save(movieSession);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        movieSessionRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    @Transactional(readOnly = true)
    public MovieSession findById(long id) {
        logger.info("searching by id is started");
        Optional<MovieSession> byId = movieSessionRepository.findById(id);
        if (byId.isPresent()) {
            logger.info("searching by id was successfully finished");
            return byId.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("MovieSession with id = %s not found", id));
    }

    public void update(MovieSession movieSession) {
        logger.info("updating is started");
        movieSessionRepository.update(movieSession.getId(), movieSession.getDate(),
                movieSession.getStartedTime(), movieSession.getPrice(), movieSession.getHall());
        logger.info("updating was successfully finished");
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findAllByDate(LocalDate date) {
        logger.info("searching all by date is started");
        List<MovieSession> movieSessions = movieSessionRepository.findByDate(date);
        if (movieSessions.isEmpty()) {
            logger.info("not found");
            throw new NotFoundException(String.format("MovieSessions with date = %s not found", date));
        }
        logger.info("searching all by date was successfully finished");
        return movieSessions;
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findAllByCinema(Cinema cinema) {
        logger.info("searching aa by cinema is started");
        List<MovieSession> movieSessions = movieSessionRepository.findByCinema(cinema);
        if (movieSessions.isEmpty()) {
            logger.info("not found");
            throw new NotFoundException(String.format("MovieSessions with cinema's name - %s not found", cinema.getName()));
        }
        logger.info("searching all by cinema was successfully finished");
        return movieSessions;
    }

    @Transactional(readOnly = true)
    public List<MovieSession> findAllByMovie(Movie movie) {
        logger.info("searching all by movie is started");
        List<MovieSession> movieSessions = movieSessionRepository.findByMovie(movie);
        if (movieSessions.isEmpty()) {
            logger.info("not found");
            throw new NotFoundException(String.format("MovieSessions with movie's name - %s not found", movie.getName()));
        }
        logger.info("searching all by movie was successfully finished");
        return movieSessions;
    }
}
