package com.example.cinema.service;

import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.user.User;
import com.example.cinema.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(long id){
        return findById(id);
    }

    @Override
    public void update(Order order) {
        orderRepository.update(order.getId(), order.getSeat());
    }

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }
}
