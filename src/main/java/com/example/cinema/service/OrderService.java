package com.example.cinema.service;

import com.example.cinema.entity.cinema.Order;
import com.example.cinema.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order findById(long id){
        return findById(id);
    }
}
