package org.example.jubjub.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequestDto {
    private String status; // ACCEPTED, REJECTED, COMPLETED 등
}