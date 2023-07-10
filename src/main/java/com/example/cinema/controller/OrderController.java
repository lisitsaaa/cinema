package com.example.cinema.controller;

import com.example.cinema.dto.OrderDto;
import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.Order;
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

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static com.example.cinema.mapper.OrderMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SeatService seatService;
    private final MovieSessionService movieSessionService;
    private final UserService userService;

    @PostMapping("/{movie_session_id}")
    public ResponseEntity<Order> create(@RequestBody @Valid SeatDto seatDto,
                                        BindingResult bindingResult,
                                        @PathVariable long movie_session_id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        MovieSession movieSession = movieSessionService.findById(movie_session_id);
        updateSeatStatus(seatDto);

        return ok(orderService.save(INSTANCE.dtoToOrder(getOrderDto(userDetails, movieSession, seatDto))));
    }

    private OrderDto getOrderDto(UserDetails userDetails, MovieSession movieSession, SeatDto seatDto){
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(userService.findByUsername(userDetails.getUsername()));
        orderDto.setMovieSession(movieSession);
        seatDto.setHall(movieSession.getHall());
        orderDto.setSeat(SeatMapper.INSTANCE.dtoToSeat(seatDto));
        return orderDto;
    }

    private void updateSeatStatus(SeatDto dto){
        seatService.update(SeatMapper.INSTANCE.dtoToSeat(dto));
    }

}
