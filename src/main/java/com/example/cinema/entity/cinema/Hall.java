package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
