package com.example.estore.services;

import com.example.estore.dto.OrderItemDTO;
import com.example.estore.dto.ProductDTO;
import com.example.estore.dto.request.CreateOrderRequest;
import com.example.estore.dto.response.OrderResponseDTO;
import com.example.estore.dto.request.OrderItemRequestDTO;
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

    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return mapToOrderDTO(order);
    }

    public OrderResponseDTO createOrder(CreateOrderRequest orderRequest) {
        User user = userService.getUserById(orderRequest.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        int totalAmount = 0;

        Order order = new Order();
        order.setUser(user);
        order.setItems(this.createOrderItems(orderRequest.getItems(), order));
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(calculateOrderTotalAmount());

        Order createdOrder = orderRepository.save(order);

        return mapToOrderDTO(createdOrder);
    }

    private List<OrderItem> createOrderItems(List<OrderItemRequestDTO> orderItemsToCreate, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequestDTO item: orderItemsToCreate) {
            Product product = productService.getProductById(item.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException(String.format("Product not found, Id : %s", item.getProductId()));
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
        }
        return orderItems;
    }

    private int calculateOrderTotalAmount() {
        return 0;
    }

    private OrderResponseDTO mapToOrderDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setUserId(order.getUser().getId());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());

        // Convert order items to OrderItemDTO with additional product details
        orderResponseDTO.setItems(order.getItems().stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toList()));

        return orderResponseDTO;
    }

    private OrderItemRequestDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemRequestDTO orderItemDTO = new OrderItemRequestDTO();
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());

        Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + orderItem.getProduct().getId()));


        return orderItemDTO;
    }
}
