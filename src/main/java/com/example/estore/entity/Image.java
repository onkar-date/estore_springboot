package com.example.estore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false) // Foreign key to Product
    private Product product; // The product this image is associated with

    @Lob // For storing large binary data like images
    @Column(name = "image", nullable = false)
    private byte[] image; // The image file stored as a LONGBLOB

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Timestamp of when the image was added

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false; // Flag to identify if this is the primary image for the product

    // Constructor to set default createdAt
    public Image(Product product, byte[] image, boolean isPrimary) {
        this.product = product;
        this.image = image;
        this.isPrimary = isPrimary;
        this.createdAt = LocalDateTime.now(); // Set createdAt to current timestamp
    }
}
