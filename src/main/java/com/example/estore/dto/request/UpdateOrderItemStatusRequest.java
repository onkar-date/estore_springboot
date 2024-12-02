package com.example.estore.dto.request;

import com.example.estore.enums.OrderItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderItemStatusRequest {

    private Long orderItemId;
    private OrderItemStatus status;
}
