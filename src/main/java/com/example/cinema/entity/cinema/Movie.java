package com.example.cinema.entity.cinema;

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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Type> types;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;

    enum AgeLimit {
        ZERO_PLUS, SIX_PLUS, TWELVE_PLUS, SIXTEEN_PLUS, EIGHTEEN_PLUS
    }

    enum Type {
        TWO_D, THREE_D
    }

    enum Genre {
        ACTION, ADVENTURE,
        COMEDY, DRAMA, FANTASY,
        HISTORICAL, HORROR, MUSICAL,
        ROMANCE, SCIENCE_FICTION, THRILLER, WESTERN
    }
}