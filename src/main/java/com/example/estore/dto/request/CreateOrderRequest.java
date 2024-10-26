package com.example.estore.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private Long userId;
    private List<OrderItemRequestDTO> items;
}
