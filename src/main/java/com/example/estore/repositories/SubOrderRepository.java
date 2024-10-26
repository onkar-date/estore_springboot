package com.example.estore.repositories;

import com.example.estore.entity.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubOrderRepository extends JpaRepository<SubOrder, Long> {
}
