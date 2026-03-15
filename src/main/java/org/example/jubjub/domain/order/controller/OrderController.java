package org.example.jubjub.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.order.dto.OrderCreateRequestDto;
import org.example.jubjub.domain.order.dto.OrderResponseDto;
import org.example.jubjub.domain.order.dto.OrderStatusUpdateRequestDto;
import org.example.jubjub.domain.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "주문 및 결제 API")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성 (결제)", description = "내 장바구니에 담긴 메뉴를 결제합니다. (토큰 자동 인식)")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @AuthenticationPrincipal Long memberId, // 👈 토큰에서 내 번호 추출!
            @RequestBody OrderCreateRequestDto request) {
        OrderResponseDto response = orderService.createOrder(memberId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 주문 내역 조회", description = "내 주문 내역을 최신순으로 조회합니다. (토큰 자동 인식)")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(
            @AuthenticationPrincipal Long memberId) { // 👈 토큰에서 내 번호 추출!
        List<OrderResponseDto> response = orderService.getMyOrders(memberId);
        return ResponseEntity.ok(response);
    }

    // 💡 [참고] 상태 변경은 사장님/관리자용 기능이라 일단 orderId를 그대로 받습니다.
    @Operation(summary = "주문 상태 변경", description = "주문의 진행 상태를 변경합니다.")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequestDto request) {
        OrderResponseDto response = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(response);
    }
}