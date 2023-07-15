package com.example.cinema.service;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.repository.HallRepository;
import com.example.cinema.repository.SeatRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class HallServiceTest {
    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private HallService hallService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private SeatService seatService;

    private Hall hall;

    @BeforeEach
    public void init(){
        Seat seat1 = seatService.findById(1);
        Seat seat2 = seatService.findById(2);
        Cinema cinema = cinemaService.findByName("MOON");

        List<Seat> seats = new ArrayList<>();
        seats.add(seat1);
        seats.add(seat2);

        hall = Hall.builder()
                .name("Small")
                .cinema(cinema)
                .seats(seats)
                .build();
    }

    @Test
    void save() {
        when(hallRepository.findByName(hall.getName())).thenReturn(Optional.empty());
        when(hallRepository.save(Mockito.any(Hall.class))).thenReturn(hall);

        Hall savedHall = hallService.save(hall);

        assertNotNull(savedHall);
    }

    @Test
    void findById() {
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));

        Hall hallById = hallService.findById(hall.getId());

        assertNotNull(hallById);
    }

    @Test
    void findAllByCinema() {
        when(hallRepository.findByCinema(hall.getCinema())).thenReturn(List.of(hall));

        List<Hall> halls = hallService.findAllByCinema(hall.getCinema());

        assertNotNull(halls);
        halls.forEach(hall1 -> assertEquals(hall.getCinema(), hall1.getCinema()));
    }

    @Test
    void findByName() {
        when(hallRepository.findByName(hall.getName())).thenReturn(Optional.of(hall));

        Hall hallByName = hallService.findByName(hall.getName());

        assertNotNull(hallByName);
        assertEquals(hall.getName(), hallByName.getName());
    }
}