package com.ecomm.ecomm.controller;

import com.ecomm.ecomm.model.Order;
import com.ecomm.ecomm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "Order Service is up and running!";
    }
}

