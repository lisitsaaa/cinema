package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class CinemaService implements AbstractService<Cinema> {
    @Autowired
    private CinemaRepository cinemaRepository;

    private final static Logger logger = Logger.getLogger(CinemaService.class.getName());

    @Override
    public Cinema save(Cinema cinema) {
        logger.info("saving is started");
        Optional<Cinema> byCityAndName = cinemaRepository.findByCityAndName(cinema.getCity(), cinema.getName());
        if (byCityAndName.isPresent()) {
            logger.info("cinema already existed");
            throw new ExistsException(String.format("Cinema with name - %s and city - %s already existed",
                    cinema.getName(),
                    cinema.getCity()));
        }
        logger.info("cinema was successfully saved");
        return cinemaRepository.save(cinema);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        cinemaRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    @Transactional(readOnly = true)
    public Cinema findById(long id) {
        logger.info("searching by id is started");
        Optional<Cinema> byId = cinemaRepository.findById(id);
        if (byId.isPresent()) {
            Cinema cinema = byId.get();
            cinema.getHalls().forEach(hall -> hall.getSeats()
                    .sort(Comparator.comparingInt(Seat::getRow)
                            .thenComparing(Seat::getSeat)));
            logger.info("searching by id was successfully finished");
            return cinema;
        }
        logger.info("cinema not found");
        throw new NotFoundException(String.format("Cinema with id = %s not found", id));
    }

    public void update(Cinema cinema) {
        logger.info("updating is started");
        cinemaRepository.update(cinema.getId(), cinema.getName());
        logger.info("updating was successfully finished");
    }

    @Transactional(readOnly = true)
    public List<Cinema> findByCity(String cityName) {
        logger.info("searching by city is started");
        List<Cinema> cinemas = cinemaRepository.findByCity(cityName);
        if (cinemas.isEmpty()) {
            logger.info("not found");
            throw new NotFoundException(String.format("Cinemas from city - %s not found", cityName));
        }
        logger.info("searching by city was successfully finished");
        return cinemas;
    }

    @Transactional(readOnly = true)
    public Cinema findByName(String name) {
        logger.info("searching by name is started");
        Optional<Cinema> byName = cinemaRepository.findByName(name);
        if (byName.isPresent()) {
            logger.info("searching by name was successfully finished");
            return byName.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("Cinema with name - %s not found", name));
    }
}
