package com.example.cinema.controller;

import com.example.cinema.dto.MovieCreatingDto;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody @Valid MovieCreatingDto movieCreatingDto,
                                        BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(movieService.save(buildMovie(movieCreatingDto)));
    }

    private boolean getValidationResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getCode(), fieldError.getDefaultMessage());
            }
            System.out.println(errors);
            return false;
        }
        return true;
    }

    private Movie buildMovie(MovieCreatingDto dto) {
        return Movie.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .duration(dto.getDuration())
                .description(dto.getDescription())
                .releaseYear(dto.getReleaseYear())
                .ageLimit(dto.getAgeLimit())
                .genres(dto.getGenres())
                .type(dto.getMovieType())
                .build();
    }
}
