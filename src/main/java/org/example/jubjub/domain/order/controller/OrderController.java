package org.example.jubjub.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.order.dto.OrderCreateRequestDto;
import org.example.jubjub.domain.order.dto.OrderResponseDto;
import org.example.jubjub.domain.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order", description = "주문 및 결제 API")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성 (결제)", description = "장바구니에 담긴 메뉴를 바탕으로 주문을 생성합니다.")
    @PostMapping("/{profileId}")
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable Long profileId,
            @RequestBody OrderCreateRequestDto request) {

        OrderResponseDto response = orderService.createOrder(profileId, request);
        return ResponseEntity.ok(response);
    }
}