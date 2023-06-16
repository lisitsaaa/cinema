package com.example.cinema.entity.seat;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Seat extends AbstractEntity {
    private int seat;
    private int row;
    private Status status;
    private PlaceType placeType;

    enum PlaceType{
        SINGLE, LOVE, FAMILY, BED
    }

    enum Status{
        FREE, OCCUPIED, BOOKED
    }
}
