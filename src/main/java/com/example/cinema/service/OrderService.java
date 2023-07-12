package com.example.cinema.service;

import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.entity.cinema.seat.SeatStatus;
import com.example.cinema.entity.user.User;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.OrderRepository;
import com.example.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService implements AbstractService<Order> {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Order save(Order order) {
        Optional<Seat> seat = seatRepository.findByHallAndRowAndSeat(order.getSeat().getHall(),
                order.getSeat().getRow(),
                order.getSeat().getSeat());
        if (seat.isPresent()) {
            if (seat.get().getSeatStatus().equals(SeatStatus.BOOKED) ||
                    seat.get().getSeatStatus().equals(SeatStatus.OCCUPIED)) {
                throw new ExistsException(String.format("seat - %s", seat.get().getSeatStatus()));
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public void remove(long id) {
        orderRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new NotFoundException(String.format("Order with id = %s not found", id));
    }

    public List<Order> findAllByUser(User user) {
        List<Order> orders = orderRepository.findAllByUser(user);
        if (orders.isEmpty()) {
            throw new NotFoundException(String.format("Orders with user's name - %s not found", user.getUsername()));
        }
        return orderRepository.findAllByUser(user);
    }
}
