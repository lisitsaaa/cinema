package com.example.cinema.entity.cinema.seat;

import com.example.cinema.entity.AbstractEntity;
import com.example.cinema.entity.cinema.Hall;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Seat extends AbstractEntity {
    private int seat;
    private int row;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne
    private Hall hall;
}
