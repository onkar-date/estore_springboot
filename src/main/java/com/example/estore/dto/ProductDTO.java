package com.example.estore.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price; // In rupees
    private int stockQuantity;
    private Long sellerId; // The ID of the seller
    private String image; // Base64 encoded image data
}

