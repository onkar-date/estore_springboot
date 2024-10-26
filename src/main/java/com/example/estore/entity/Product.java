package com.example.estore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products") // Specify table name
@Data // Generates getters and setters
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the product

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; // Description of the product

    @Column(nullable = false)
    private int price; // Store price in paise (or cents)

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity; // Quantity available in stock

    // Reference to the seller (User)
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false) // Foreign key to the User entity
    private User seller; // The seller of this product

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;
}
