package com.example.cinema.dto;

import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderDto {
    private MovieSession movieSession;
    private User user;

    @NotNull @NotEmpty @NotBlank
    private Seat seat;
}
