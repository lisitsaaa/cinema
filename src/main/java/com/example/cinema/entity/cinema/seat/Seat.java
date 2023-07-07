package com.example.cinema.entity.cinema.seat;

import com.example.cinema.entity.AbstractEntity;
import com.example.cinema.entity.cinema.Hall;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Seat extends AbstractEntity {
    private int row;
    private int seat;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @ManyToOne
    @JsonIgnore
    private Hall hall;
}
