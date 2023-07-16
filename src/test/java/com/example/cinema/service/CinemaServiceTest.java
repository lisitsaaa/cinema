package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.repository.CinemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {
    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private CinemaService cinemaService;

    @Autowired
    private HallService hallService;

    private Cinema cinema;

    @BeforeEach
    public void init(){
        Hall hall1 = hallService.findByName("Standard");

        List<Hall> halls = new ArrayList<>();
        halls.add(hall1);

        cinema = Cinema.builder()
                .name("MOON")
                .city("Minsk")
                .halls(halls)
                .build();
    }

    @Test
    void CinemaRepository_Save_ReturnCinema() {
        when(cinemaRepository.findByCityAndName(cinema.getCity(), cinema.getName()))
                .thenReturn(Optional.empty());
        when(cinemaRepository.save(Mockito.any(Cinema.class))).thenReturn(cinema);

        Cinema savedCinema = cinemaService.save(cinema);

        assertNotNull(savedCinema);
        assertEquals(cinema.getName(), savedCinema.getName());
        assertEquals(cinema.getCity(), savedCinema.getCity());
    }

    @Test
    void CinemaRepository_FindAllByCity_ReturnCinemaList() {
        when(cinemaRepository.findByCity(cinema.getCity())).thenReturn(List.of(cinema));

        List<Cinema> cinemas = cinemaService.findByCity(cinema.getCity());

        assertNotNull(cinemas);
        cinemas.forEach(cinema1 -> assertEquals(cinema.getCity(), cinema1.getCity()));
    }

    @Test
    void CinemaRepository_FindByName_ReturnCinema() {
        when(cinemaRepository.findByName(cinema.getName())).thenReturn(Optional.of(cinema));

        Cinema cinemaByName = cinemaService.findByName(cinema.getName());

        assertNotNull(cinemaByName);
        assertEquals(cinema.getName(), cinemaByName.getName());
    }
}