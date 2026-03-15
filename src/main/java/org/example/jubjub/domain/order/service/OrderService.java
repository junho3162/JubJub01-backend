package org.example.jubjub.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.cart.entity.Cart;
import org.example.jubjub.domain.cart.repository.CartRepository;
import org.example.jubjub.domain.order.dto.OrderCreateRequestDto;
import org.example.jubjub.domain.order.dto.OrderResponseDto;
import org.example.jubjub.domain.order.dto.OrderStatusUpdateRequestDto;
import org.example.jubjub.domain.order.entity.Order;
import org.example.jubjub.domain.order.entity.OrderItem;
import org.example.jubjub.domain.order.repository.OrderItemRepository;
import org.example.jubjub.domain.order.repository.OrderRepository;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.store.repository.StoreRepository;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.domain.user.repository.MemberProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final StoreRepository storeRepository;

    public OrderResponseDto createOrder(Long memberId, OrderCreateRequestDto request) {
        // 🚨 1. findById -> findByMemberId 로 변경!
        MemberProfile profile = memberProfileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 💡 진짜 프로필 번호 꺼내기
        Long profileId = profile.getId();

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        // 🚨 2. memberId가 아니라 방금 꺼낸 profileId 로 장바구니를 조회해야 합니다!
        List<Cart> storeCartItems = cartRepository.findAllByMemberProfileId(profileId).stream()
                .filter(cart -> cart.getStore().getId().equals(request.getStoreId()))
                .collect(Collectors.toList());

        if (storeCartItems.isEmpty()) {
            throw new IllegalArgumentException("해당 매장의 장바구니가 비어있습니다.");
        }

        // 3. 삐빅! 총 결제 금액 계산기
        int totalAmount = storeCartItems.stream()
                .mapToInt(cart -> cart.getMenu().getPrice() * cart.getQuantity())
                .sum();

        // 고객이 앱에서 본 금액과 실제 계산된 금액이 다르면 결제 차단 (보안 검증)
        if (!request.getExpectedTotalAmount().equals(totalAmount)) {
            throw new IllegalArgumentException("결제 예상 금액이 변경되었습니다. 장바구니를 다시 확인해 주세요.");
        }

        // 4. 고유한 주문번호 생성 (예: ORD-20231025-A1B2C3D4)
        String orderNumber = "ORD-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // 5. 드디어 '주문(Order)' 생성 및 저장!
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .memberProfile(profile)
                .store(store)
                .status(Order.OrderStatus.PENDING) // 처음엔 '대기중' 상태
                .totalAmount(totalAmount)
                .finalPaymentAmount(totalAmount)   // (나중에 할인 쿠폰 로직이 생기면 여기서 뺌)
                .isEcoContainer(request.getIsEcoContainer())
                .build();
        Order savedOrder = orderRepository.save(order);

        // 6. 주문 상세 내역(OrderItem) 묶어서 저장!
        List<OrderItem> orderItems = storeCartItems.stream()
                .map(cart -> OrderItem.builder()
                        .order(savedOrder)
                        .menu(cart.getMenu())
                        .orderPrice(cart.getMenu().getPrice()) // 나중에 메뉴 가격이 올라도 영수증 가격은 유지되도록
                        .count(cart.getQuantity())
                        .build())
                .collect(Collectors.toList());
        orderItemRepository.saveAll(orderItems);

        // 7. 결제 끝났으니 장바구니 비우기 🗑️
        cartRepository.deleteAll(storeCartItems);

        // 8. 사장님과 고객에게 보여줄 예쁜 응답 반환
        return new OrderResponseDto(savedOrder);
    }

    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusUpdateRequestDto request) {
        // (기존 코드 그대로 유지)
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Order.OrderStatus newStatus;
        try {
            newStatus = Order.OrderStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 주문 상태입니다.");
        }

        order.updateStatus(newStatus);
        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getMyOrders(Long memberId) { // 🚨 3. 파라미터 profileId -> memberId로 변경
        // 토큰에서 나온 memberId로 프로필을 먼저 찾습니다.
        MemberProfile profile = memberProfileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 💡 찾아온 프로필의 getId() 를 사용해서 주문 목록 조회!
        List<Order> orders = orderRepository.findAllByMemberProfileIdOrderByCreatedAtDesc(profile.getId());

        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }
}