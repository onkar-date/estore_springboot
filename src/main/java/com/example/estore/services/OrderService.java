package com.example.estore.services;

import com.example.estore.dto.CreateOrderRequest;
import com.example.estore.dto.OrderDTO;
import com.example.estore.dto.OrderItemDTO;
import com.example.estore.dto.ProductDTO;
import com.example.estore.entity.Order;
import com.example.estore.entity.OrderItem;
import com.example.estore.entity.Product;
import com.example.estore.entity.User;
import com.example.estore.enums.OrderStatus;
import com.example.estore.exceptions.ResourceNotFoundException;
import com.example.estore.repositories.OrderRepository;
import com.example.estore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return mapToOrderDTO(order);
    }

    public OrderDTO createOrder(CreateOrderRequest orderRequest) {
        User user = userService.getUserById(orderRequest.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found"); // Throw exception if user not found
        }

        List<OrderItem> orderItems = new ArrayList<>();
        int totalAmount = 0;

        for (OrderItemDTO itemDTO : orderRequest.getItems()) {
            ProductDTO productDTO = productService.getProductById(itemDTO.getProductId());
            if (productDTO == null) {
                throw new ResourceNotFoundException("Product", "id", itemDTO.getProductId()); // Throw exception if product not found
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(new Product());
            orderItem.getProduct().setId(productDTO.getId()); // Set product ID
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(productDTO.getPrice()); // Use price from ProductDTO

            // Set the order reference to a temporary order object
            orderItems.add(orderItem);
            totalAmount += orderItem.getPrice() * orderItem.getQuantity(); // Calculate total amount
        }

        // Create a temporary Order object
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);

        // Now associate each OrderItem with the Order
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order); // Set the order reference
        }

        // Set the populated order items
        order.setItems(orderItems);

        // Save the order (this will also save order items due to cascade)
        Order createdOrder = orderRepository.save(order);

        return mapToOrderDTO(createdOrder);
    }

    private OrderDTO mapToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalAmount(order.getTotalAmount());

        // Convert order items to OrderItemDTO with additional product details
        orderDTO.setItems(order.getItems().stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toList()));

        return orderDTO;
    }

    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());

        // Fetch the product details from the database using the product ID
        Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + orderItem.getProduct().getId()));

        // Set the additional fields
        orderItemDTO.setName(product.getName());
        orderItemDTO.setPrice(product.getPrice());

        if (product.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(product.getImage());
            orderItemDTO.setImage(base64Image);
        }

        return orderItemDTO;
    }
}
