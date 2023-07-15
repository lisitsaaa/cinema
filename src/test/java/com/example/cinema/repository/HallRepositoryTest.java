package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HallRepositoryTest {
    @Autowired private HallRepository hallRepository;
    @Autowired private CinemaRepository cinemaRepository;

    @Test
    void HallRepository_findByName_ReturnHall() {
        String hallName = "Standard";
        Hall hall = hallRepository.findByName(hallName).get();

        assertNotNull(hall);
        assertEquals(hallName, hall.getName());

    }

    @Test
    void HallRepository_findByCinema_ReturnHallList() {
        String cinemaName = "Nova";
        String cinemaCity = "Minsk";
        Cinema cinema = cinemaRepository.findByCityAndName(cinemaCity, cinemaName).get();

        List<Hall> halls = hallRepository.findByCinema(cinema);

        assertNotNull(halls);
        halls.forEach(hall -> assertEquals(cinema, hall.getCinema()));
    }
}