package com.example.estore.dto;

import com.example.estore.enums.OrderItemStatus;
import lombok.Data;

@Data
public class OrderItemDTO {

    private String name;
    private String description;
    private int quantity;
    private int price;
    private String base64Image;
    private OrderItemStatus status;
}
