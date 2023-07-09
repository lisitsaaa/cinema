package com.example.cinema.dto;

import com.example.cinema.entity.cinema.movie.AgeLimit;
import com.example.cinema.entity.cinema.movie.Genre;
import com.example.cinema.entity.cinema.movie.MovieType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MovieDto {
    private String name;
    private String image;
    private int duration;
    private int releaseYear;
    private String description;
    private AgeLimit ageLimit;
    private List<Genre> genres;
    private MovieType movieType;
}
