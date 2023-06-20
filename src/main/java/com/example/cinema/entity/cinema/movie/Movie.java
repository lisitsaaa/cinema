package com.example.cinema.entity.cinema.movie;

import com.example.cinema.entity.AbstractEntity;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Movie extends AbstractEntity {
    private String name;
    private String image;
    private int duration;
    private int releaseYear;
    private String description;

    @Enumerated(EnumType.STRING)
    private AgeLimit ageLimit;

    @Enumerated(EnumType.STRING)
    private MovieType types;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;
}

