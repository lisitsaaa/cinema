package com.example.cinema.dto;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.SeatType;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Setter @Getter
public class SeatDto {
    @NotNull
    @Min(value = 0)
    private int seat;
    @NotNull
    @Min(value = 0)
    private int row;
    private SeatStatus seatStatus;
    private SeatType seatType;
    private Hall hall;
}
