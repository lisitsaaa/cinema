package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.repository.CinemaRepository;
import com.example.cinema.repository.MovieSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MovieSessionServiceTest {
    @Mock
    private MovieSessionRepository movieSessionRepository;

    @InjectMocks
    private MovieSessionService movieSessionService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallService hallService;

    private MovieSession movieSession;

    @BeforeEach
    public void init() {
        Movie movie = movieService.findByName("Spider-Man: No Way Home");
        Cinema cinema = cinemaRepository.findByCityAndName("Rechitsa", "SKYLINE").get();
        Hall hall = hallService.findByName("Small");

        movieSession = MovieSession.builder()
                .date(LocalDate.parse("2023-07-18"))
                .startedTime(LocalTime.parse("21:30:00"))
                .price(13.5)
                .movie(movie)
                .cinema(cinema)
                .hall(hall)
                .build();
    }

    @Test
    void MovieSessionRepository_Save_ReturnMovieSession() {
        when(movieSessionRepository.findByDateAndStartedTimeAndHall(movieSession.getDate(),
                movieSession.getStartedTime(), movieSession.getHall())).thenReturn(Optional.empty());
        when(movieSessionRepository.save(Mockito.any(MovieSession.class))).thenReturn(movieSession);

        MovieSession savedMovieSession = movieSessionService.save(movieSession);

        assertNotNull(savedMovieSession);
    }

    @Test
    void MovieSessionRepository_FindById_ReturnMovieSession() {
        when(movieSessionRepository.findById(movieSession.getId())).thenReturn(Optional.of(movieSession));

        MovieSession movieSessionById = movieSessionService.findById(movieSession.getId());

        assertNotNull(movieSessionById);
    }

    @Test
    void MovieSessionRepository_FindAllByDate_ReturnMovieSessionList() {
        when(movieSessionRepository.findByDate(movieSession.getDate())).thenReturn(List.of(movieSession));

        List<MovieSession> movieSessions = movieSessionService.findAllByDate(movieSession.getDate());

        assertNotNull(movieSessions);
        movieSessions.forEach(movieSession1 -> assertEquals(movieSession.getDate(), movieSession1.getDate()));
    }

    @Test
    void MovieSessionRepository_FindAllByCinema_ReturnMovieSessionList() {
        when(movieSessionRepository.findByCinema(movieSession.getCinema())).thenReturn(List.of(movieSession));

        List<MovieSession> movieSessions = movieSessionService.findAllByCinema(movieSession.getCinema());

        assertNotNull(movieSessions);
        movieSessions.forEach(movieSession1 -> assertEquals(movieSession.getCinema(), movieSession1.getCinema()));
    }

    @Test
    void MovieSessionRepository_FindAllByMovie_ReturnMovieSessionList() {
        when(movieSessionRepository.findByMovie(movieSession.getMovie())).thenReturn(List.of(movieSession));

        List<MovieSession> movieSessions = movieSessionService.findAllByMovie(movieSession.getMovie());

        assertNotNull(movieSessions);
        movieSessions.forEach(movieSession1 -> assertEquals(movieSession.getMovie(), movieSession1.getMovie()));
    }
}