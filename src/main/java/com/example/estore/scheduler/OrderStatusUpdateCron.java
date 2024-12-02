//package com.example.estore.scheduler;
//
//import com.example.estore.dto.OrderItemDTO;
//import com.example.estore.entity.Order;
//import com.example.estore.enums.OrderItemStatus;
//import com.example.estore.enums.OrderStatus;
//import com.example.estore.services.OrderItemService;
//import com.example.estore.services.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Component
//public class OrderStatusUpdateCron {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private OrderItemService orderItemService;
//
//    @Scheduled(fixedRate = 1000 * 60 * 30) // Run every 30 minutes
//    @Transactional
//    public void updateOrderStatus() {
//        // Fetch orders that are either pending or shipped (not delivered)
//        List<Long> ordersToUpdate = orderService.findOrderIdsByStatus(OrderStatus.PENDING);
//        ordersToUpdate.addAll(orderService.findOrderIdsByStatus(OrderStatus.SHIPPED));
//
//        System.out.println("Orders to update: " + ordersToUpdate);
//
//        ordersToUpdate.forEach(orderId -> {
//            // Fetch order items for the given order ID
//            List<OrderItemDTO> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
//
//            // Flags to track the statuses of the order items
//            boolean allDelivered = true;
//            boolean hasPending = false;
//            boolean hasShipped = false;
//
//            for (OrderItemDTO item : orderItems) {
//                if (item.getStatus() == OrderItemStatus.PENDING) {
//                    hasPending = true; // If any item is PENDING, the order is PENDING
//                } else if (item.getStatus() == OrderItemStatus.SHIPPED) {
//                    hasShipped = true; // If any item is SHIPPED, we need to check the shipping status
//                } else if (item.getStatus() != OrderItemStatus.DELIVERED && item.getStatus() != OrderItemStatus.CANCELLED) {
//                    allDelivered = false; // If not all items are delivered, set flag to false
//                }
//            }
//
//            // Determine the new order status based on the statuses of the order items
//            OrderStatus newStatus = getNewStatus(hasPending, allDelivered, hasShipped);
//
//            // Fetch the order and update its status if needed
//            Order order = orderService.getOrderById(orderId);
//            if (order != null && !order.getStatus().equals(newStatus)) {
//                order.setStatus(newStatus);
//                orderService.saveOrder(order); // Save the updated order
//            }
//
//            System.out.println("Updated order " + orderId + " status to " + newStatus);
//        });
//    }
//
//    private static OrderStatus getNewStatus(boolean hasPending, boolean allDelivered, boolean hasShipped) {
//        OrderStatus newStatus;
//        if (hasPending) {
//            newStatus = OrderStatus.PENDING; // If any OrderItem is PENDING, the Order is PENDING
//        } else if (allDelivered) {
//            newStatus = OrderStatus.DELIVERED; // If all OrderItems are DELIVERED and no pending items, the Order is DELIVERED
//        } else if (hasShipped) {
//            newStatus = OrderStatus.SHIPPED; // If any OrderItem is SHIPPED but not DELIVERED, the Order is SHIPPED
//        } else {
//            newStatus = OrderStatus.PENDING; // Default to PENDING if conditions don't match
//        }
//        return newStatus;
//    }
//
//
//}
