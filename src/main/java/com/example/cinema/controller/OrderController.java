package com.example.cinema.controller;

import com.example.cinema.dto.OrderDto;
import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.mapper.SeatMapper;
import com.example.cinema.service.MovieSessionService;
import com.example.cinema.service.OrderService;
import com.example.cinema.service.SeatService;
import com.example.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.cinema.controller.util.Validator.checkBindingResult;
import static com.example.cinema.mapper.OrderMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SeatService seatService;
    private final MovieSessionService movieSessionService;
    private final UserService userService;

    @PostMapping("/admin/{movie_session_id}")
    public ResponseEntity<OrderDto> create(@RequestBody @Valid SeatDto seatDto,
                                           BindingResult bindingResult,
                                           @PathVariable long movie_session_id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.orderToDto(orderService.save(getOrder(movie_session_id, seatDto, userDetails))));
    }

    private Order getOrder(long movie_session_id, SeatDto seatDto, UserDetails userDetails){
        MovieSession movieSession = movieSessionService.findById(movie_session_id);
        Seat seat = seatService.findByHallAndRowAndSeat(movieSession.getHall(),
                seatDto.getRow(),
                seatDto.getSeat());

        if (seat.getSeatStatus().equals(SeatStatus.BOOKED)) {
            throw new ExistsException(String.format("Sorry, seat - %s", SeatStatus.BOOKED));
        }

        seat.setSeatStatus(seatDto.getSeatStatus());
        seatService.updateSeatStatus(seat);

        return Order.builder()
                .user(userService.findByUsername(userDetails.getUsername()))
                .movieSession(movieSession)
                .seat(seat)
                .build();
    }

    @DeleteMapping("/admin/{id}")
    public void remove(@PathVariable long id) {
        orderService.remove(id);
    }

    @GetMapping("/find-by-user")
    public ResponseEntity<List<Order>> getAllByUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ok(orderService.findAllByUser(userService.findByUsername(userDetails.getUsername())));
    }
}
