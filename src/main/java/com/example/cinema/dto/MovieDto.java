package com.example.cinema.dto;

import com.example.cinema.entity.cinema.movie.AgeLimit;
import com.example.cinema.entity.cinema.movie.Genre;
import com.example.cinema.entity.cinema.movie.MovieType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MovieDto {
    @NotNull @NotEmpty @NotBlank
    private String name;

    @NotNull @NotEmpty @NotBlank
    private String image;

    @NotNull @Min(value = 0)
    private int duration;

    @NotNull @Min(value = 0)
    private int releaseYear;

    @NotNull @NotEmpty @NotBlank
    private String description;

    private AgeLimit ageLimit;
    private List<Genre> genres;
    private MovieType movieType;
}
