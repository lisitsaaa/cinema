package com.example.cinema.service;

import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.user.User;
import com.example.cinema.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Autowired
    private MovieSessionService movieSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private SeatService seatService;

    private Order order;

    @BeforeEach
    public void init(){
        MovieSession movieSession = movieSessionService.findById(1);
        User user = userService.findByUsername("lisitsaaa");
        Seat seat = seatService.findById(1);

        order = Order.builder()
                .user(user)
                .movieSession(movieSession)
                .seat(seat)
                .build();
    }

    @Test
    void OrderRepository_Save_ReturnOrder() {
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        Order savedOrder = orderService.save(order);

        assertNotNull(savedOrder);
    }

    @Test
    void OrderRepository_FindById_ReturnOrder() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order oderById = orderService.findById(order.getId());

        assertNotNull(oderById);
    }

    @Test
    void OrderRepository_FindAllByUser_ReturnOrderList() {
        when(orderRepository.findAllByUser(order.getUser())).thenReturn(List.of(order));

        List<Order> orders = orderService.findAllByUser(order.getUser());

        assertNotNull(orders);
        orders.forEach(order1 -> assertEquals(order.getUser(), order1.getUser()));
    }
}