package com.example.cinema.controller;

import com.example.cinema.dto.MovieCreatingDto;
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
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody @Valid MovieCreatingDto movieCreatingDto,
                                        BindingResult bindingResult){
        return ResponseEntity.ok(movieService.save(buildMovie(movieCreatingDto)));
    }


    private Movie buildMovie(MovieCreatingDto dto){
        return Movie.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .duration(dto.getDuration())
                .description(dto.getDescription())
                .releaseYear(dto.getReleaseYear())
                .ageLimit(dto.getAgeLimit())
                .genres(List.of(dto.getGenre()))
                .types(dto.getMovieType())
                .build();
    }
}
