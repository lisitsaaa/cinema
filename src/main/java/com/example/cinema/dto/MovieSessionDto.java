package com.example.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MovieSessionDto {
    private LocalDate date;
    private LocalTime startedTime;
    private double price;
    private String movieName;
    private String cinemaName;
}
