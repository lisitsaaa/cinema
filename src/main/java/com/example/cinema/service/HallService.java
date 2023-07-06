package com.example.cinema.service;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
@Transactional
public class HallService {
    @Autowired
    private HallRepository hallRepository;

    public Hall save(Hall hall){
        hall.getSeats().forEach(seat -> seat.setHall(hall));
        return hallRepository.save(hall);
    }

    @Transactional(readOnly = true)
    public Hall findById(long id){
        Optional<Hall> byId = hallRepository.findById(id);
        if (byId.isPresent()) {
            Hall hall = byId.get();
            hall.getSeats()
                    .sort(Comparator.comparingInt(Seat::getRow)
                            .thenComparing(Seat::getSeat));
            return hall;
        }
        throw new RuntimeException("incorrect id");
    }
}
