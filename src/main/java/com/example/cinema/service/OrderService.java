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

@Service
@Transactional
public class OrderService implements AbstractService<Order>{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order){
        return orderRepository.save(order);
    }

    @Override
    public void remove(long id) {
        orderRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new NotFoundException(String.format("Order with id = %s not found", id));
    }

    public List<Order> findAllByUser(User user){
        List<Order> orders = orderRepository.findAllByUser(user);
        if (orders.isEmpty()) {
            throw new NotFoundException(String.format("Orders with user's name - %s not found", user.getUsername()));
        }
        return orderRepository.findAllByUser(user);
    }
}
