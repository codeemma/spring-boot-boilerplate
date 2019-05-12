package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.model.Order;
import com.springboilerplate.springboilerplate.model.User;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order assignDriver(Long orderId, Long driverId);
    List<Order> findAll();
    List<Order> findClientOrder(User client);
    List<Order> findDriverOrder(User driver);

}
