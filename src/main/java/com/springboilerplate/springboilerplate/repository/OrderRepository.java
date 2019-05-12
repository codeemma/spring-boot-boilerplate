package com.springboilerplate.springboilerplate.repository;

import com.springboilerplate.springboilerplate.model.Order;
import com.springboilerplate.springboilerplate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClient(User client);
    List<Order> findByDriver(User driver);
}
