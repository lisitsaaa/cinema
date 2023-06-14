package com.example.cinema.entity.film;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Film extends AbstractEntity {
    private String name;
    private double price;
    private String image;
    private LocalDate firstDay;
    private LocalDate lastDay;
    private LocalTime startTime;
    private int duration;
    private int releaseYear;
    private String description;

    @Enumerated(EnumType.STRING)
    private AgeLimit ageLimit;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Type> types;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;

}

enum AgeLimit{
    ZERO_PLUS, SIX_PLUS, TWELVE_PLUS, SIXTEEN_PLUS, EIGHTEEN_PLUS
}

enum Type{
    TWO_D, THREE_D
}

enum Genre {
    ACTION, ADVENTURE,
    COMEDY, DRAMA, FANTASY,
    HISTORICAL, HORROR, MUSICAL,
    ROMANCE, SCIENCE_FICTION, THRILLER, WESTERN
}