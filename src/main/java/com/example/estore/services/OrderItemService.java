package com.example.estore.services;

import com.example.estore.dto.OrderItemDTO;
import com.example.estore.dto.request.UpdateOrderItemStatusRequest;
import com.example.estore.entity.Order;
import com.example.estore.entity.OrderItem;
import com.example.estore.entity.User;
import com.example.estore.enums.OrderItemStatus;
import com.example.estore.enums.OrderStatus;
import com.example.estore.enums.UserType;
import com.example.estore.exceptions.ResourceNotFoundException;
import com.example.estore.repositories.OrderItemRepository;
import com.example.estore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ImageService imageService;

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItemDTO> getSellerOrders(Long sellerId) {
        User user = userService.getUserById(sellerId);
        if (!UserType.SELLER.equals(user.getRole())) {
            throw new AuthorizationDeniedException("User is not a seller !!");
        }
        List<OrderItem> sellerOrderItems = orderItemRepository.findBySellerId(sellerId);
        return sellerOrderItems.stream().map(this::mapToOrderItemDTO).collect(Collectors.toList());
    }

    public void updateOrderItemStatus(UpdateOrderItemStatusRequest request, User user) {
        if (!UserType.SELLER.equals(user.getRole())) {
            throw new AuthorizationDeniedException("You must be seller to do this action !!");
        }

        OrderItem orderItemToUpdate = orderItemRepository
                .findById(request.getOrderItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found !!"));

        if (!Objects.equals(orderItemToUpdate.getSeller().getId(), user.getId())) {
            throw new AuthorizationDeniedException("User is not authorized to do this action");
        }

        orderItemToUpdate.setStatus(request.getStatus());
        if (OrderItemStatus.DELIVERED.equals(request.getStatus())) {
            orderItemToUpdate.setDeliveryDate(new Date());
        }

        orderItemRepository.save(orderItemToUpdate);
        updateParentOrderStatus(orderItemToUpdate.getOrder().getId());

    }

    public OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setDescription(orderItem.getProduct().getDescription());
        orderItemDTO.setName(orderItem.getProduct().getName());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setBase64Image(imageService.getBase64Image(orderItem.getImage().getImage()));
        orderItemDTO.setStatus(orderItem.getStatus());

        return orderItemDTO;
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.getOrderItemsByOrderId(orderId)
                .stream()
                .map(this::mapToOrderItemDTO)
                .collect(Collectors.toList());
    }

    private void updateParentOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found !!"));

        List<OrderItemDTO> orderItems = getOrderItemsByOrderId(orderId);
        // Flags to track the statuses of the order items
        OrderStatus newStatus = getOrderStatus(orderItems);

        // Fetch the order and update its status if needed
        if (!order.getStatus().equals(newStatus)) {
            order.setStatus(newStatus);
            orderRepository.save(order); // Save the updated order
        }

        System.out.println("Updated order " + orderId + " status to " + newStatus);
    }

    private static OrderStatus getOrderStatus(List<OrderItemDTO> orderItems) {
        boolean hasDelivered = false;
        boolean hasPending = false;
        boolean hasShipped = false;

        for (OrderItemDTO item : orderItems) {
            if (item.getStatus() == OrderItemStatus.PENDING) {
                hasPending = true;
            } else if (item.getStatus() == OrderItemStatus.SHIPPED) {
                hasShipped = true;
            } else if (item.getStatus() == OrderItemStatus.DELIVERED) {
                hasDelivered = true;
            }
        }

        return getNewStatus(hasPending, hasDelivered, hasShipped);
    }

    // If any item is PENDING, the order is PENDING
    // If nothing is PENDING, and any ONE order item is SHIPPED then parent order status will be SHIPPED
    // If no item is PENDING or SHIPPED, then order items are either in DELIVERED or CANCELLED status
    // If any one is DELIVERED then parent order status will be DELIVERED
    // else parent order status will be cancelled as all items are CANCELLED
    private static OrderStatus getNewStatus(boolean hasPending, boolean hasDelivered, boolean hasShipped) {
        OrderStatus newStatus;
        if (hasPending) {
            newStatus = OrderStatus.PENDING;
        } else if (hasShipped) {
            newStatus = OrderStatus.SHIPPED;
        }  else if (hasDelivered) {
            newStatus = OrderStatus.DELIVERED;
        }else {
            newStatus = OrderStatus.CANCELLED;
        }
        return newStatus;
    }
}
