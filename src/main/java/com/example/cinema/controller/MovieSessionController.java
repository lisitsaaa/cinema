package com.example.cinema.controller;

import com.example.cinema.dto.MovieSessionDto;
import com.example.cinema.entity.cinema.Hall;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.cinema.controller.util.Validator.checkBindingResult;
import static com.example.cinema.mapper.MovieSessionMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/movie-session")
@RequiredArgsConstructor
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final CinemaService cinemaService;
    private final HallService hallService;
    private final MovieService movieService;

    @PostMapping("/admin")
    public ResponseEntity<MovieSessionDto> create(@RequestBody @Valid MovieSessionDto dto,
                                                  BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        dto.setMovie(movieService.findByName(dto.getMovieName()));
        dto.setCinema(cinemaService.findByName(dto.getCinemaName()));
        dto.setHall(hallService.findByName(dto.getHallName()));
        return ok(INSTANCE.movieSessionToDto(movieSessionService.save(INSTANCE.dtoToMovieSession(dto))));
    }

    @DeleteMapping("/admin/{id}")
    public void remove(@PathVariable long id) {
        movieSessionService.remove(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSessionDto> getById(@PathVariable long id) {
        return ok(INSTANCE.movieSessionToDto(movieSessionService.findById(id)));
    }

    @PostMapping("/find-by-movie")
    public ResponseEntity<List<MovieSessionDto>> getAllByMovie(@RequestBody @Valid MovieSessionDto dto,
                                                               BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        List<MovieSessionDto> movieSessionDtoList = new ArrayList<>();
        movieSessionService.findAllByMovie(movieService.findByName(dto.getMovieName()))
                .forEach(movieSession -> movieSessionDtoList.add(INSTANCE.movieSessionToDto(movieSession)));
        return ok(movieSessionDtoList);
    }

    @PostMapping("/find-by-cinema")
    public ResponseEntity<List<MovieSessionDto>> getAllByCinema(@RequestBody @Valid MovieSessionDto dto,
                                                                BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        List<MovieSessionDto> movieSessionDtoList = new ArrayList<>();
        movieSessionService.findAllByCinema(cinemaService.findByName(dto.getMovieName()))
                .forEach(movieSession -> movieSessionDtoList.add(INSTANCE.movieSessionToDto(movieSession)));
        return ok(movieSessionDtoList);
    }

    @PostMapping("/find-by-date")
    public ResponseEntity<List<MovieSessionDto>> getAllByDate(@RequestBody @Valid MovieSessionDto dto,
                                                              BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        List<MovieSessionDto> movieSessionDtoList = new ArrayList<>();
        movieSessionService.findAllByDate(dto.getDate())
                .forEach(movieSession -> movieSessionDtoList.add(INSTANCE.movieSessionToDto(movieSession)));
        return ok(movieSessionDtoList);
    }

    @PostMapping("/admin/update/{id}")
    public ResponseEntity<MovieSessionDto> update(@PathVariable long id,
                                                  @RequestBody @Valid MovieSession movieSession,
                                                  BindingResult bindingResult) {
        checkBindingResult(bindingResult);

        MovieSession movieSessionById = movieSessionService.findById(id);
        if (!String.valueOf(movieSession.getDate()).isEmpty()) {
            movieSessionById.setDate(movieSession.getDate());
        }
        if (!String.valueOf(movieSession.getStartedTime()).isEmpty()) {
            movieSessionById.setStartedTime(movieSession.getStartedTime());
        }
        if (!String.valueOf(movieSession.getPrice()).isEmpty()) {
            movieSessionById.setPrice(movieSession.getPrice());
        }
        movieSessionService.update(movieSessionById);
        return ok(INSTANCE.movieSessionToDto(movieSessionById));
    }
}
