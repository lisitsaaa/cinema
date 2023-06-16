package com.example.cinema.entity.seat;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

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
        SINGLE, LOVE, FAMILY, BED
    }

    enum Status{
        FREE, OCCUPIED, BOOKED
    }
}
