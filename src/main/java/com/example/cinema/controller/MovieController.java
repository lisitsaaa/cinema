package com.example.cinema.controller;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.example.cinema.controller.util.Validator.checkBindingResult;
import static com.example.cinema.mapper.MovieMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody @Valid MovieDto dto,
                                           BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.movieToDto(movieService.save(INSTANCE.dtoToMovie(dto))));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        movieService.remove(id);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieService.findAll()
                .forEach(movie -> movieDtoList.add(INSTANCE.movieToDto(movie)));
        return ok(movieDtoList);
    }

    @PostMapping("/find-by-name")
    public ResponseEntity<MovieDto> getByName(@RequestBody @Valid MovieDto dto,
                                              BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.movieToDto(movieService.findByName(dto.getName())));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable long id,
                                           @RequestBody @Valid Movie movie,
                                           BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.movieToDto(getUpdatingMovie(id, movie)));
    }

    private Movie getUpdatingMovie(long id, Movie movie) {
        Movie movieById = movieService.findById(id);
        if (!movie.getName().isEmpty()) {
            movieById.setName(movie.getName());
        }
        if (!movie.getDescription().isEmpty()) {
            movieById.setDescription(movie.getDescription());
        }
        if (!movie.getImage().isEmpty()) {
            movieById.setImage(movie.getImage());
        }
        if (!String.valueOf(movie.getDuration()).isEmpty()) {
            movieById.setDuration(movie.getDuration());
        }
        if (!String.valueOf(movie.getReleaseYear()).isEmpty()) {
            movieById.setReleaseYear(movie.getReleaseYear());
        }
        movieService.update(movieById);
        return movieById;
    }
}
