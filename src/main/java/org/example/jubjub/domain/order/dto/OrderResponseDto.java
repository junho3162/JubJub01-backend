package org.example.jubjub.domain.order.dto;

import lombok.Getter;
import org.example.jubjub.domain.order.entity.Order;
import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private final Long orderId;
    private final String orderNumber;
    private final String storeName;
    private final String status;
    private final Integer finalPaymentAmount;
    private final LocalDateTime createdAt;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.storeName = order.getStore().getName();
        this.status = order.getStatus().name();
        this.finalPaymentAmount = order.getFinalPaymentAmount();
        this.createdAt = order.getCreatedAt();
    }
}