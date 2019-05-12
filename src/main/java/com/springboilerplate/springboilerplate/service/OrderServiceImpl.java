package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.model.Order;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.OrderRepository;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order assignDriver(Long orderId, Long driverId) {
        Order order = orderRepository.getOne(orderId);
        User driver = userRepository.getOne(driverId);
        order.setDriver(driver);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findClientOrder(User client) {
        return orderRepository.findByClient(client);
    }

    @Override
    public List<Order> findDriverOrder(User driver) {
        return orderRepository.findByDriver(driver);
    }
}
