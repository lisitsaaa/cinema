package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.user.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Order extends AbstractEntity {
    @OneToOne
    private MovieSession movieSession;

    @OneToOne
    private User user;

    @OneToOne
    private Seat seat;

//    @OneToMany
//    private List<Seat> seats;

}
