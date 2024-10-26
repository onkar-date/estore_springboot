package com.example.estore.services;

import com.example.estore.entity.SubOrder;
import com.example.estore.repositories.SubOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubOrderService {

    @Autowired
    SubOrderRepository subOrderRepository;

    public SubOrder saveSubOrder(SubOrder subOrder) {
        return subOrderRepository.save(subOrder);
    }
}
