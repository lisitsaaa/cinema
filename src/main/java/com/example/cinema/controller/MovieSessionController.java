package com.example.cinema.controller;

import com.example.cinema.dto.MovieSessionDto;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.service.CinemaService;
import com.example.cinema.service.HallService;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/movie-session")
public class MovieSessionController {
    @Autowired
    private MovieSessionService movieSessionService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private HallService hallService;

    @Autowired
    private MovieService movieService;


    @PostMapping
    public ResponseEntity<MovieSession> create(@RequestBody @Valid MovieSessionDto dto,
                                               BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        return ok(movieSessionService.save(buildMovieSession(dto)));
    }

    private MovieSession buildMovieSession(MovieSessionDto dto) {
        return MovieSession.builder()
                .date(dto.getDate())
                .startedTime(dto.getStartedTime())
                .price(dto.getPrice())
                .movie(movieService.findByName(dto.getMovieName()))
                .cinema(cinemaService.findByName(dto.getCinemaName()))
                .hall(hallService.findByName(dto.getHallName()))
                .build();
    }

    @GetMapping("/{id}")
    public MovieSession findById(@PathVariable long id) {
        return movieSessionService.findById(id);
    }
}
