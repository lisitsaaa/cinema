package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Hall extends AbstractEntity {
    private String name;

    @ManyToOne
    private Cinema cinema;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;
}
