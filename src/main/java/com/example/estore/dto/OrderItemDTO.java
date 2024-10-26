package com.example.estore.dto;

import com.example.estore.enums.OrderItemStatus;
import com.example.estore.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderItemDTO {

    private String name;
    private String description;
    private String quantity;
    private String price;
    private String image;
    private OrderItemStatus status;
}
