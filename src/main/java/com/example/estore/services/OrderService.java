package com.example.estore.services;

import com.example.estore.dto.OrderItemDTO;
import com.example.estore.dto.request.CreateOrderRequest;
import com.example.estore.dto.response.OrderResponseDTO;
import com.example.estore.dto.request.OrderItemRequestDTO;
import com.example.estore.entity.*;
import com.example.estore.enums.OrderItemStatus;
import com.example.estore.enums.OrderStatus;
import com.example.estore.enums.SubOrderStatus;
import com.example.estore.exceptions.InsufficientStockException;
import com.example.estore.exceptions.ResourceNotFoundException;
import com.example.estore.repositories.OrderRepository;
import com.example.estore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private SubOrderService subOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return mapToOrderDTO(order);
    }

    public OrderResponseDTO createOrder(CreateOrderRequest orderRequest) {
        User user = validateUser(orderRequest.getUserId());

        Order order = initializeOrder(user);
        int totalAmount = 0;

        // Save the main order first
        Order createdOrder = orderRepository.save(order);

        for (OrderItemRequestDTO item : orderRequest.getItems()) {
            totalAmount += processOrderItem(item, createdOrder); // Pass the saved order here
        }

        createdOrder.setTotalAmount(totalAmount);
        Order updatedOrder = orderRepository.save(createdOrder); // Update the total amount in the main order

        return mapToOrderDTO(updatedOrder);
    }

    private User validateUser(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    private Order initializeOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    private int processOrderItem(OrderItemRequestDTO item, Order order) {
        Product product = validateProduct(item.getProductId());
        checkStockAvailability(product, item.getQuantity());
        updateProductStock(product, item.getQuantity());

        int itemTotal = calculateItemTotal(item, product);
        OrderItem orderItem = createOrderItem(item, product, itemTotal);

        // Set the reference to the main order
        orderItem.setOrder(order); // Set the order reference

        // Create a new sub-order for this item
        SubOrder subOrder = createSubOrder(product, itemTotal, order);


        // Now associate the order item with the saved sub-order
        orderItem.setSubOrder(subOrder); // Set the sub-order reference

        // Save the order item
        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem); // Save the order item

        order.getItems().add(savedOrderItem); // Add the order item to the order

        return itemTotal; // Return total for this item
    }


    private Product validateProduct(Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException(String.format("Product not found, Id : %s", productId));
        }
        return product;
    }

    private void checkStockAvailability(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(String.format("Insufficient stock for product: %s", product.getName()));
        }
    }

    private void updateProductStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productService.updateProduct(product); // Assuming this method updates the product in the database
    }

    private int calculateItemTotal(OrderItemRequestDTO item, Product product) {
        return item.getQuantity() * product.getPrice();
    }

    private OrderItem createOrderItem(OrderItemRequestDTO item, Product product, int itemTotal) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItem.setTotalPrice(itemTotal);
        orderItem.setName(product.getName());
        orderItem.setDescription(product.getDescription());
        orderItem.setStatus(OrderItemStatus.PENDING);

        // Fetch the primary image for the product
        Image primaryImage = imageService.getPrimaryImageByProductId(product.getId());
        if (primaryImage != null) {
            orderItem.setImage(primaryImage); // Set the image for the order item
        } else {
            // Handle case where no image is found
            throw new ResourceNotFoundException("Primary image not found for product Id: " + product.getId());
        }

        return orderItem;
    }

    private SubOrder createSubOrder(Product product, int itemTotal, Order order) {
        SubOrder subOrder = new SubOrder();
        subOrder.setStatus(SubOrderStatus.PENDING);
        subOrder.setSeller(product.getSeller()); // Set the seller for the sub-order
        subOrder.setTotalAmount(itemTotal); // Initialize total amount for sub-order
        subOrder.setShippingDate(null); // Set shipping date if available
        subOrder.setDeliveryDate(null); // Set delivery date if available

        // Associate sub-order with the main order
        subOrder.setOrder(order);

        return subOrderService.saveSubOrder(subOrder);
    }

    private OrderResponseDTO mapToOrderDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setUserId(order.getUser().getId());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (OrderItem orderItem: order.getItems()) {
            OrderItemDTO orderItemDTO = orderItemService.mapToOrderItemDTO(orderItem);
            orderItemDTOS.add(orderItemDTO);
        }
        // Convert order items to OrderItemDTO with additional product details
        orderResponseDTO.setItems(orderItemDTOS);

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
