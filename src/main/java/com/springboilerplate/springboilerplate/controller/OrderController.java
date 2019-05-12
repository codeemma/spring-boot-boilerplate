package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.model.Order;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.service.OrderService;
import com.springboilerplate.springboilerplate.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole(CLIENT)")
    @PostMapping
    public Order saveOrder(@RequestBody Order order) throws Exception {
        User client =UserUtils.getLoggedInUser();
        order.setClient(client);

        return orderService.saveOrder(order);
    }


    @GetMapping
    public List<Order> getOrders() throws Exception {

        return orderService.findAll();
    }

    @GetMapping("/client")
    public List<Order> getClientOrders() throws Exception {

        User client =UserUtils.getLoggedInUser();
        return orderService.findClientOrder(client);
    }
}
