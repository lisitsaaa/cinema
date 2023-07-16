package com.example.cinema.service;

import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import com.example.cinema.entity.cinema.seat.SeatType;
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

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @Autowired
    private HallService hallService;

    private Seat seat;

    @BeforeEach
    public void init() {
        Hall hall = hallService.findByName("Small");
        seat = Seat.builder()
                .row(1)
                .seat(1)
                .seatType(SeatType.SINGLE)
                .seatStatus(SeatStatus.FREE)
                .hall(hall)
                .build();

    }

    @Test
    void SeatService_Save_ReturnSeat() {
        when(seatRepository.findByHallAndRowAndSeat(seat.getHall(), seat.getRow(), seat.getSeat()))
                .thenReturn(empty());
        when(seatRepository.save(Mockito.any(Seat.class))).thenReturn(seat);

        Seat savedSeat = seatService.save(seat);

        assertNotNull(savedSeat);
    }

    @Test
    void SeatService_FindById_ReturnSeat() {
        when(seatRepository.findById(seat.getId())).thenReturn(of(seat));

        Seat seatById = seatService.findById(seat.getId());

        assertNotNull(seatById);
    }

    @Test
    void SeatService_FindByHallAndRowAndSeat_ReturnSeat() {
        when(seatRepository.findByHallAndRowAndSeat(seat.getHall(), seat.getRow(), seat.getSeat()))
                .thenReturn(of(seat));

        Seat seat1 = seatService.findByHallAndRowAndSeat(seat.getHall(), seat.getRow(), seat.getSeat());
        assertNotNull(seat1);
        assertEquals(seat.getRow(), seat1.getRow());
        assertEquals(seat.getSeat(), seat1.getSeat());
    }
}