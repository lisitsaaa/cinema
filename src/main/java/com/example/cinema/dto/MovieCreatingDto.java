package com.example.cinema.dto;

import com.example.cinema.entity.cinema.movie.AgeLimit;
import com.example.cinema.entity.cinema.movie.Genre;
import com.example.cinema.entity.cinema.movie.MovieType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MovieCreatingDto {
    private String name;
    private String image;
    private int duration;
    private int releaseYear;
    private String description;
    private AgeLimit ageLimit;
    private Genre genre;
    private MovieType movieType;
}
