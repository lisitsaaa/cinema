package com.example.cinema.service;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SeatService implements AbstractService<Seat> {
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Seat save(Seat seat) {
        if (seatRepository.findByHallAndRowAndSeat(seat.getHall(),
                        seat.getRow(),
                        seat.getSeat())
                .isPresent()) {
            throw new ExistsException(String.format("Seat with row - %s and seat - %s in hall - %s already existed",
                    seat.getRow(),
                    seat.getRow(),
                    seat.getHall().getName()));
        }
        return seatRepository.save(seat);
    }

    @Override
    public void remove(long id) {
        seatRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Seat findById(long id) {
        Optional<Seat> byId = seatRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new NotFoundException(String.format("Seat with id - %s not found", id));
    }

    public Seat updateSeatStatus(Hall hall, SeatDto dto) {
        Seat seat = findByHallAndRowAndSeat(hall, dto.getRow(), dto.getSeat());
        seatRepository.update(seat.getId(), dto.getSeatStatus());
        return seat;
    }

    @Transactional(readOnly = true)
    public Seat findByHallAndRowAndSeat(Hall hall, int row, int seat) {
        Optional<Seat> byHallAndRowAndSeat = seatRepository.findByHallAndRowAndSeat(hall, row, seat);
        if (byHallAndRowAndSeat.isPresent()) {
            return byHallAndRowAndSeat.get();
        }
        throw new NotFoundException(String.format("MovieSession with hall - %s, row - %s and seat - %s not found",
                hall.getName(), row, seat));

    }
}
