package com.example.cinema.entity.order;

import com.example.cinema.entity.AbstractEntity;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.Seat;
import com.example.cinema.entity.user.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Order extends AbstractEntity {
    @OneToMany
    private List<Seat> seats;

    @OneToOne
    private User user;

    @OneToOne
    private MovieSession movieSession;
}
