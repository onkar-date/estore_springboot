package com.example.estore.dto;

import com.example.estore.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private Date orderDate;
    private OrderStatus status;
    private int totalAmount;
}
