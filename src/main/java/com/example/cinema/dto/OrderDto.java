package com.example.cinema.dto;

import com.example.cinema.entity.cinema.seat.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderDto {
    private List<Seat> seats;
}
