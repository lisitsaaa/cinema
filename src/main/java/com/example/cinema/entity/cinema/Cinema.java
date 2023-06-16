package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Cinema extends AbstractEntity {
    private String name;
    private String city;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Hall> halls;
}
