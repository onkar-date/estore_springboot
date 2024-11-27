package com.example.estore.repositories;

import com.example.estore.entity.Order;
import com.example.estore.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.id FROM Order o WHERE o.status = :status")
    List<Long> findOrderIdsByStatus(OrderStatus status);
}
