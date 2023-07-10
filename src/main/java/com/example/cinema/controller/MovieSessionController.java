package com.example.cinema.controller;

import com.example.cinema.dto.MovieSessionDto;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.service.CinemaService;
import com.example.cinema.service.HallService;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static com.example.cinema.mapper.MovieSessionMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/movie-session")
@RequiredArgsConstructor
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final CinemaService cinemaService;
    private final HallService hallService;
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieSession> create(@RequestBody @Valid MovieSessionDto dto,
                                               BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        dto.setMovie(movieService.findByName(dto.getMovieName()));
        dto.setCinema(cinemaService.findByName(dto.getCinemaName()));
        dto.setHall(hallService.findByName(dto.getHallName()));

        return ok(movieSessionService.save(INSTANCE.dtoToMovieSession(dto)));
    }

    @GetMapping("/{id}")
    public MovieSession findById(@PathVariable long id) {
        return movieSessionService.findById(id);
    }
}
