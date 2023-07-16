package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SeatRepositoryTest {
    @Autowired private SeatRepository seatRepository;
    @Autowired private HallRepository hallRepository;

    @Test
    void seatRepository_FindByHallAndRowAndSeat_ReturnSeat() {
        String hallName = "Standard";
        int rowNumber = 1;
        int seatNumber = 1;

        Hall hall = hallRepository.findByName(hallName).get();
        Seat seat = seatRepository.findByHallAndRowAndSeat(hall, rowNumber, seatNumber).get();

        assertNotNull(seat);
        assertEquals(hall, seat.getHall());
        assertEquals(rowNumber, seat.getRow());
        assertEquals(seatNumber, seat.getSeat());
    }
}
