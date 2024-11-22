package com.example.estore.services;

import com.example.estore.dto.OrderItemDTO;
import com.example.estore.entity.OrderItem;
import com.example.estore.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ImageService imageService;

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setDescription(orderItem.getProduct().getDescription());
        orderItemDTO.setName(orderItem.getProduct().getName());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setBase64Image(imageService.getBase64Image(orderItem.getImage().getImage()));
        orderItemDTO.setStatus(orderItem.getStatus());

        return orderItemDTO;
    }
}