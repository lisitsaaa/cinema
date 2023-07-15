package com.example.cinema.service;

import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.user.User;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class OrderService implements AbstractService<Order> {
    @Autowired
    private OrderRepository orderRepository;

    private final static Logger logger = Logger.getLogger(CinemaService.class.getName());

    @Override
    public Order save(Order order) {
        logger.info("saving is started");
        return orderRepository.save(order);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        orderRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(long id) {
        logger.info("searching by id is started");
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            logger.info("searching by id was successfully finished");
            return order.get();
        }
        logger.info("not found");
        throw new NotFoundException(String.format("Order with id = %s not found", id));
    }

    public List<Order> findAllByUser(User user) {
        logger.info("searching all by user is started");
        List<Order> orders = orderRepository.findAllByUser(user);
        if (orders.isEmpty()) {
            logger.info("not found");
            throw new NotFoundException(String.format("Orders with user's name - %s not found", user.getUsername()));
        }
        logger.info("searching all by user was successfully finished");
        return orderRepository.findAllByUser(user);
    }
}
