package com.example.cinema.controller;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody @Valid MovieDto dto,
                                        BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(movieService.save(buildMovie(dto)));
    }

    private Movie buildMovie(MovieDto dto) {
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
