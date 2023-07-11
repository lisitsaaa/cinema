package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import com.example.cinema.entity.cinema.seat.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Hall extends AbstractEntity {
    @NotNull @NotEmpty @NotBlank
    private String name;

    @ManyToOne
    @JsonIgnore
    private Cinema cinema;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
}
