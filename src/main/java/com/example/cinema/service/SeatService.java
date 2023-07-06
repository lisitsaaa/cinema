package com.example.cinema.service;

import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public Seat save(Seat seat){
        return seatRepository.save(seat);
    }

//    public Seat findHall(){
//
//    }
}
