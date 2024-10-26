package com.example.estore.dto.response;

import com.example.estore.dto.request.OrderItemRequestDTO;
import com.example.estore.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private List<OrderItemRequestDTO> items;
    private Date orderDate;
    private OrderStatus status;
    private int totalAmount;
}
