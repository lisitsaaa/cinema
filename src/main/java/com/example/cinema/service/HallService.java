package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HallService implements AbstractService<Hall> {
    @Autowired
    private HallRepository hallRepository;

    @Override
    public Hall save(Hall hall) {
        hall.getSeats().forEach(seat -> seat.setHall(hall));
        return hallRepository.save(hall);
    }

    @Override
    public void remove(long id) {
        hallRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Hall findById(long id) {
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

    @Override
    public void update(Hall hall) {
        hallRepository.update(hall.getId(), hall.getName());
    }

    @Transactional(readOnly = true)
    public List<Hall> findAllByCinema(Cinema cinema){
        return hallRepository.findByCinema(cinema);
    }

    @Transactional(readOnly = true)
    public Hall findByName(String name) {
        Optional<Hall> byName = hallRepository.findByName(name);
        if (byName.isPresent()) {
            return byName.get();
        }
        throw new RuntimeException("incorrect name");
    }
}
