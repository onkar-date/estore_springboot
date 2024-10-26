package com.example.estore.dto;

import lombok.Data;

@Data
public class ImageDTO {

    private Long id;
    private String base64Image;
    private Boolean isPrimary;
}
