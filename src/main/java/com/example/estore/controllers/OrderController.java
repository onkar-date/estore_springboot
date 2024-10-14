package com.example.estore.controllers;

import com.example.estore.dto.CreateOrderRequest;
import com.example.estore.dto.OrderDTO;
import com.example.estore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        OrderDTO createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }
}
