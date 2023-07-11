package com.example.cinema.dto;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MovieSessionDto {
    @NotNull @NotEmpty @NotBlank
    @FutureOrPresent
    private LocalDate date;

    @NotNull @NotEmpty @NotBlank
    private LocalTime startedTime;

    @NotNull @Min(value = 0)
    private double price;

    private Movie movie;
    private Cinema cinema;
    private Hall hall;

    private String movieName;
    private String cinemaName;
    private String hallName;
}
