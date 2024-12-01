package com.example.estore.repositories;

import com.example.estore.entity.OrderItem;
import com.example.estore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi from OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.seller = :seller")
    List<OrderItem> findBySeller(User seller);
}
