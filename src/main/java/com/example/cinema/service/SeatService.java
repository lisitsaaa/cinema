package com.example.cinema.service;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public Seat save(Seat seat){
        return seatRepository.save(seat);
    }

    @Transactional(readOnly = true)
    public Seat findByHallAndRowAndSeat(Hall hall, int row, int seat){
        Optional<Seat> byHallAndRowAndSeat = seatRepository.findByHallAndRowAndSeat(hall, row, seat);
        if (byHallAndRowAndSeat.isPresent()) {
            return byHallAndRowAndSeat.get();
        }
        throw new RuntimeException("incorrect data");
    }

    @Transactional(readOnly = true)
    public Seat findById(long id){
        Optional<Seat> byId = seatRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new RuntimeException("try again:)");
    }

    public void updateSeatStatus(long id, SeatStatus seatStatus){
        seatRepository.update(id, seatStatus);
    }
}
