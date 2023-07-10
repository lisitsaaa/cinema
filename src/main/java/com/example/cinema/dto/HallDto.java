package com.example.cinema.dto;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.seat.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class HallDto {
    private String name;
    private List<Seat> seats;
    private Cinema cinema;
}
