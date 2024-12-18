package com.example.estore.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private MultipartFile image;
    private Long sellerId;
}

