package com.example.cinema.service;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class SeatService implements AbstractService<Seat> {
    @Autowired
    private SeatRepository seatRepository;

    private final static Logger logger = Logger.getLogger(CinemaService.class.getName());

    @Override
    public Seat save(Seat seat) {
        logger.info("saving is started");
        if (seatRepository.findByHallAndRowAndSeat(seat.getHall(),
                        seat.getRow(),
                        seat.getSeat())
                .isPresent()) {
            logger.info("already existed");
            throw new ExistsException(String.format("Seat with row - %s and seat - %s in hall - %s already existed",
                    seat.getRow(),
                    seat.getRow(),
                    seat.getHall().getName()));
        }
        logger.info("seat session was successfully saved");
        return seatRepository.save(seat);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        seatRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    @Transactional(readOnly = true)
    public Seat findById(long id) {
        logger.info("searching by id is started");
        Optional<Seat> byId = seatRepository.findById(id);
        if (byId.isPresent()) {
            logger.info("searching by id was successfully finished");
            return byId.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("Seat with id - %s not found", id));
    }

    public void updateSeatStatus(Seat seat) {
        logger.info("updating is started");
        seatRepository.update(seat.getId(), seat.getSeatStatus());
        logger.info("updating was successfully finished");
    }

    @Transactional(readOnly = true)
    public Seat findByHallAndRowAndSeat(Hall hall, int row, int seat) {
        logger.info("searching by hall, row and seat is started");
        Optional<Seat> byHallAndRowAndSeat = seatRepository.findByHallAndRowAndSeat(hall, row, seat);
        if (byHallAndRowAndSeat.isPresent()) {
            logger.info("searching by hall, row and seat was successfully finished");
            return byHallAndRowAndSeat.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("MovieSession with hall - %s, row - %s and seat - %s not found",
                hall.getName(), row, seat));

    }
}
