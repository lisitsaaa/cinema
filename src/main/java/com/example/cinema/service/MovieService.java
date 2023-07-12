package com.example.cinema.service;

import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class MovieService implements AbstractService<Movie> {
    @Autowired
    private MovieRepository movieRepository;

    private final static Logger logger = Logger.getLogger(CinemaService.class.getName());

    @Override
    public Movie save(Movie movie) {
        logger.info("saving is started");
        if (movieRepository.findByNameAndReleaseYear(movie.getName(), movie.getReleaseYear()).isPresent()) {
            logger.info("movie already existed");
            throw new ExistsException(String.format("Movie with name - %s and release year - %5 already existed",
                    movie.getName(),
                    movie.getReleaseYear()));
        }
        logger.info("movie was successfully saved");
        return movieRepository.save(movie);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        movieRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(long id) {
        logger.info("searching by id is started");
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            logger.info("searching by id was successfully finished");
            return movie.get();
        }
        logger.info("movie not found");
        throw new NotFoundException(String.format("Movie with id = %s not found", id));
    }

    public void update(Movie movie) {
        logger.info("updating is started");
        movieRepository.update(movie.getId(), movie.getName(),
                movie.getDescription(), movie.getImage(),
                movie.getDuration(), movie.getReleaseYear());
        logger.info("updating was successfully finished");
    }

    @Transactional(readOnly = true)
    public List<Movie> findAll() {
         logger.info("searching all is started");
        return movieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Movie findByName(String name) {
         logger.info("searching by name is started");
        Optional<Movie> byName = movieRepository.findByName(name);
        if (byName.isPresent()) {
            logger.info("searching by name was successfully finished");
            return byName.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("Movie with name - %s not found", name));
    }
}
