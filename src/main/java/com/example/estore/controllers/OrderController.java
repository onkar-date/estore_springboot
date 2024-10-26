package com.example.estore.controllers;

import com.example.estore.dto.request.CreateOrderRequest;
import com.example.estore.dto.response.OrderResponseDTO;
import com.example.estore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderById(id);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }
}
