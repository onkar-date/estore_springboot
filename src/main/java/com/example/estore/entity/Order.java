package com.example.estore.entity;

import com.example.estore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // Specify table name
@Data // Generates getters and setters
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to User
    private User user; // User who placed the order

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items; // List of items in the order

    @Column(name = "order_date", nullable = false)
    private Date orderDate; // Date when the order was placed

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status; // Status of the order (e.g., PENDING, SHIPPED)

    @Column(name = "total_amount", nullable = false)
    private double totalAmount; // Total amount for the order
}

