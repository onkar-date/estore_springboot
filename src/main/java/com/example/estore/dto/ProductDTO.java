package com.example.estore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private Long sellerId;
    private List<ImageDTO> images = new ArrayList<>();
}

