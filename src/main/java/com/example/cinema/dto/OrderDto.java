package com.example.cinema.dto;

import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderDto {
    private MovieSession movieSession;
    private User user;
    private Seat seat;
}
