package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class HallService implements AbstractService<Hall> {
    @Autowired
    private HallRepository hallRepository;

    private final static Logger logger = Logger.getLogger(CinemaService.class.getName());

    @Override
    public Hall save(Hall hall) {
        logger.info("saving is started");
        if (hallRepository.findByName(hall.getName()).isPresent()) {
            logger.info("hall already existed");
            throw new ExistsException(String.format("Hall with name - %s already existed", hall.getName()));
        }
        hall.getSeats().forEach(seat -> seat.setHall(hall));
        logger.info("hall was successfully saved");
        return hallRepository.save(hall);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        hallRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    @Transactional(readOnly = true)
    public Hall findById(long id) {
        logger.info("searching by id is started");
        Optional<Hall> byId = hallRepository.findById(id);
        if (byId.isPresent()) {
            Hall hall = byId.get();
            hall.getSeats()
                    .sort(Comparator.comparingInt(Seat::getRow)
                            .thenComparing(Seat::getSeat));
            logger.info("searching by id was successfully finished");
            return hall;
        }
        logger.info("user not found");
        throw new NotFoundException(String.format("Hall with id = %s not found", id));
    }

    public void update(Hall hall) {
        logger.info("updating is started");
        hallRepository.update(hall.getId(), hall.getName());
        logger.info("updating was successfully finished");
    }

    @Transactional(readOnly = true)
    public List<Hall> findAllByCinema(Cinema cinema){
        logger.info("searching by cinema is started");
        List<Hall> halls = hallRepository.findByCinema(cinema);
        if (halls.isEmpty()) {
            logger.info("not found");
            throw new NotFoundException(String.format("Hall with cinema's name - %s not found", cinema.getName()));
        }
        logger.info("searching by cinema was successfully finished");
        return halls;
    }

    @Transactional(readOnly = true)
    public Hall findByName(String name) {
        logger.info("searching by name is started");
        Optional<Hall> byName = hallRepository.findByName(name);
        if (byName.isPresent()) {
            logger.info("searching by name was successfully finished");
            return byName.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("Hall with name - %s not found", name));
    }
}
