package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Order;
import com.example.cinema.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void OrderRepository_FindAllByUser_ReturnOrder() {
        String username = "lisitsaaa";
        User user = userRepository.findByUsername(username).get();

        List<Order> orders = orderRepository.findAllByUser(user);

        assertNotNull(orders);
        orders.forEach(order -> assertEquals(user, order.getUser()));
    }
}