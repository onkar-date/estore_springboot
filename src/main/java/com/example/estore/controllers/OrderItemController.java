package com.example.estore.controllers;


import com.example.estore.dto.request.UpdateOrderItemStatusRequest;

import com.example.estore.entity.User;
import com.example.estore.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PutMapping("/update-status")
    public ResponseEntity<String> updateOrderItemStatus(
            Authentication authentication,
            @RequestBody UpdateOrderItemStatusRequest request
    ) {
        User user = (User) authentication.getPrincipal();
        orderItemService.updateOrderItemStatus(request, user);
        return ResponseEntity.ok("Order Item Status Updated Successfully!!");
    }
}
