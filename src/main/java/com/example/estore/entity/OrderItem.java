package com.example.estore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items") // Specify table name
@Data // Generates getters and setters
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Foreign key to Order
    private Order order; // The order this item belongs to

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Foreign key to Product
    private Product product; // The product being ordered

    @Column(nullable = false)
    private int quantity; // Quantity of the product ordered

    @Column(nullable = false)
    private double price; // Price of the product at the time of the order
}
