package com.example.estore.controllers;

import com.example.estore.dto.OrderItemDTO;
import com.example.estore.dto.request.CreateOrderRequest;
import com.example.estore.dto.response.OrderResponseDTO;
import com.example.estore.services.OrderItemService;
import com.example.estore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderDTOById(id);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/user-orders/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(@PathVariable Long customerId) {
        List<OrderResponseDTO> orderResponseDTO = orderService.getCustomerOrders(customerId);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping("/seller-orders/{sellerId}")
    public ResponseEntity<List<OrderItemDTO>> getSellerOrders(@PathVariable Long sellerId) {
        List<OrderItemDTO> orderItems = orderItemService.getSellerOrders(sellerId);
        return ResponseEntity.ok(orderItems);
    }
}
