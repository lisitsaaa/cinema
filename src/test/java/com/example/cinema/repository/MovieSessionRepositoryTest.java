package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.movie.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieSessionRepositoryTest {
    @Autowired private MovieSessionRepository movieSessionRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private CinemaRepository cinemaRepository;
    @Autowired private HallRepository hallRepository;

    @Test
    void movieSessionRepository_FindByDate_ReturnMovieSessionList() {
        LocalDate date = LocalDate.parse("2023-07-30");
        List<MovieSession> movieSessions = movieSessionRepository.findByDate(date);

        assertNotNull(movieSessions);
        movieSessions.forEach(movieSession -> assertEquals(date, movieSession.getDate()));
    }

    @Test
    void movieSessionRepository_FindByCinema_ReturnMovieSessionList() {
        String cinemaName = "MOON";
        String cinemaCity = "Minsk";
        Cinema cinema = cinemaRepository.findByCityAndName(cinemaCity, cinemaName).get();

        List<MovieSession> movieSessions = movieSessionRepository.findByCinema(cinema);

        assertNotNull(movieSessions);
        movieSessions.forEach(movieSession -> assertEquals(cinemaName, movieSession.getCinema().getName()));
        movieSessions.forEach(movieSession -> assertEquals(cinemaCity, movieSession.getCinema().getCity()));
    }

    @Test
    void movieSessionRepository_FindByMovie_ReturnMovieSessionList() {
        String movieName = "Spider-Man: No Way Home";
        int releaseYear = 2021;
        Movie movie = movieRepository.findByNameAndReleaseYear(movieName, releaseYear).get();

        List<MovieSession> movieSessions = movieSessionRepository.findByMovie(movie);

        assertNotNull(movieSessions);
        movieSessions.forEach(movieSession -> assertEquals(movieName, movieSession.getMovie().getName()));
        movieSessions.forEach(movieSession -> assertEquals(releaseYear, movieSession.getMovie().getReleaseYear()));
    }

    @Test
    void movieSessionRepository_FindByDateAndStartedTimeAndHall_ReturnMovieSession() {
        String hallName = "Standard";
        LocalDate date = LocalDate.parse("2023-07-30");
        LocalTime startedTime = LocalTime.parse("21:30:00");

        Hall hall = hallRepository.findByName(hallName).get();

        MovieSession movieSession = movieSessionRepository.findByDateAndStartedTimeAndHall(date, startedTime, hall).get();

        assertNotNull(movieSession);
        assertEquals(hall, movieSession.getHall());
        assertEquals(date, movieSession.getDate());
        assertEquals(startedTime, movieSession.getStartedTime());
    }
}