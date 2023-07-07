package com.example.cinema.controller;

import com.example.cinema.dto.OrderDto;
import com.example.cinema.entity.order.Order;
import com.example.cinema.service.MovieSessionService;
import com.example.cinema.service.OrderService;
import com.example.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MovieSessionService movieSessionService;

    @Autowired
    private UserService userService;

    @PostMapping("/{movie_session_id}")
    public ResponseEntity<Order> create(@PathVariable long movie_session_id,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody @Valid OrderDto dto,
                                        BindingResult bindingResult){
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orderService.save(buildOrder(movie_session_id, userDetails, dto)));
    }

    private Order buildOrder(long movie_session_id, UserDetails userDetails, OrderDto dto){
        return Order.builder()
                .user(userService.findByUsername(userDetails.getUsername()))
                .movieSession(movieSessionService.findById(movie_session_id))
                .seats(dto.getSeats())
                .build();
    }

    //update seat status!
}
