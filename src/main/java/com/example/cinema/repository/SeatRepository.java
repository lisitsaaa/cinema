package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByHallAndRowAndSeat(Hall hall, int row, int seat);

    List<Seat> findByHall(Hall hall);

    @Modifying
    @Query("update Seat s set s.seatStatus=:seatStatus where s.id=:id")
    void update(@Param("id") long id, @Param("seatStatus") SeatStatus seatStatus);
}
