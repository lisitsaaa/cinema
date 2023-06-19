package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Seat extends AbstractEntity {
    private int seat;
    private int row;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    enum PlaceType{
        SINGLE, LOVE_SEAT, FAMILY_SEAT, BED
    }

    enum Status{
        FREE, OCCUPIED, BOOKED
    }
}
