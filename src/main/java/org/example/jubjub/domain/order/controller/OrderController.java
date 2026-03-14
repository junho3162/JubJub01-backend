package org.example.jubjub.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.order.dto.OrderCreateRequestDto;
import org.example.jubjub.domain.order.dto.OrderResponseDto;
import org.example.jubjub.domain.order.dto.OrderStatusUpdateRequestDto;
import org.example.jubjub.domain.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "주문 상태 변경", description = "주문의 진행 상태(ACCEPTED, REJECTED 등)를 변경합니다.")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequestDto request) {

        OrderResponseDto response = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 주문 내역 조회", description = "특정 사용자의 주문 내역을 최신순으로 조회합니다.")
    @GetMapping("/{profileId}")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(@PathVariable Long profileId) {

        List<OrderResponseDto> response = orderService.getMyOrders(profileId);
        return ResponseEntity.ok(response);
    }
}