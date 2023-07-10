package com.example.cinema.service;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeatService implements AbstractService<Seat>{
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Seat save(Seat seat){
        return seatRepository.save(seat);
    }

    @Override
    public void remove(long id) {
        seatRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Seat findById(long id){
        Optional<Seat> byId = seatRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new RuntimeException("try again:)");
    }

    @Override
    public void update(Seat entity) {
        Seat seat = findByHallAndRowAndSeat(entity.getHall(), entity.getRow(), entity.getSeat());
        seatRepository.update(seat.getId(), seat.getSeatStatus());
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
    public List<Seat> findByHall(Hall hall){
        return seatRepository.findByHall(hall);
    }
}
