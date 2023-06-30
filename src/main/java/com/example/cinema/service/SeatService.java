package com.example.cinema.service;

import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public Seat save(Seat seat){
        return seatRepository.save(seat);
    }
}
