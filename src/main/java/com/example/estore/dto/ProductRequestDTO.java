package com.example.estore.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private MultipartFile image;  // Multipart file for image
    private Long sellerId;
}

