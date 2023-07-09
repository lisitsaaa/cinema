package com.example.cinema.controller;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.service.MovieSessionService;
import com.example.cinema.service.OrderService;
import com.example.cinema.service.SeatService;
import com.example.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private MovieSessionService movieSessionService;

    @Autowired
    private UserService userService;

    @PostMapping("/{movie_session_id}")
    public ResponseEntity<Order> create(@RequestBody @Valid SeatDto dto,
                                        BindingResult bindingResult,
                                        @PathVariable long movie_session_id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        MovieSession movieSession = movieSessionService.findById(movie_session_id);
        return ok(orderService.save(buildOrder(movieSession, dto, userDetails)));
    }

    private Order buildOrder(MovieSession movieSession,
                             SeatDto dto,
                             UserDetails userDetails) {
        return Order.builder()
                .movieSession(movieSession)
                .user(userService.findByUsername(userDetails.getUsername()))
                .seat(getUpdatingUser(movieSession.getHall(), dto))
                .build();
    }

    private Seat getUpdatingUser(Hall hall, SeatDto dto){
        Seat seat = seatService.findByHallAndRowAndSeat(hall, dto.getRow(), dto.getSeat());
        seatService.updateSeatStatus(seat.getId(), dto.getSeatStatus());
        return seatService.findById(seat.getId());
    }
}
