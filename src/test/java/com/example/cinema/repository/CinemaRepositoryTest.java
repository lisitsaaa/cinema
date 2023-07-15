package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CinemaRepositoryTest {
    @Autowired
    private CinemaRepository cinemaRepository;
    private Cinema cinema;

    @BeforeEach
    public void init() {
        cinema = Cinema.builder().name("Nova").city("Minsk").build();
    }

    @Test
    @DisplayName("JUnit test for save method")
    void CinemaRepository_Save_ReturnSavedCinema() {
        Cinema savedCinema = cinemaRepository.save(cinema);

        assertNotNull(savedCinema);
    }

    @Test
    @DisplayName("JUnit test for findByName method")
    void CinemaRepository_findByName_ReturnCinema() {
        Cinema cinemaByName = cinemaRepository.findByName(cinema.getName()).get();

        assertNotNull(cinemaByName);
        assertEquals(cinema.getName(), cinemaByName.getName());
    }

    @Test
    @DisplayName("JUnit test for findByCity method")
    void CinemaRepository_findByCity_ReturnCinemaList() {
        List<Cinema> cinemas = cinemaRepository.findByCity(cinema.getCity());

        assertNotNull(cinemas);
        cinemas.forEach(cinemaByCity -> assertEquals(cinema.getCity(), cinemaByCity.getCity()));
    }

    @Test
    @DisplayName("JUnit test for findByCityAndName method")
    void CinemaRepository_findByCityAndName_ReturnCinema() {
        Cinema cinemaByCityAndName = cinemaRepository.findByCityAndName(cinema.getCity(), cinema.getName()).get();

        assertNotNull(cinemaByCityAndName);
        assertEquals(cinema.getCity(), cinemaByCityAndName.getCity());
        assertEquals(cinema.getName(), cinemaByCityAndName.getName());
    }
}