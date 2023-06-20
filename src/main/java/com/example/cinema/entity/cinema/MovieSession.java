package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import com.example.cinema.entity.cinema.movie.Movie;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class MovieSession extends AbstractEntity {
    private LocalDate date;
    private LocalDate startedTime;
    private double price;

    @OneToOne
    private Movie movie;

    @OneToOne
    private Cinema cinema;
}
