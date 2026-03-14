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

    public OrderResponseDto createOrder(Long profileId, OrderCreateRequestDto request) {
        // 1. 사용자 및 매장 정보 확인
        MemberProfile profile = memberProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        // 2. 해당 매장에 담은 장바구니 항목만 쏙 골라내기
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
        // 1. 주문 번호로 주문서를 찾습니다.
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        // 2. 사장님이 보낸 문자열(String) 상태를 Enum 타입으로 변환합니다.
        Order.OrderStatus newStatus;
        try {
            newStatus = Order.OrderStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 주문 상태입니다.");
        }

        // 3. 주문 상태 업데이트! (JPA의 더티 체킹 덕분에 save()를 안 해도 DB에 자동 반영됩니다)
        order.updateStatus(newStatus);

        // 4. 변경된 결과를 반환
        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true) // 데이터 변경 없이 읽기만 하므로 속도가 빠릅니다!
    public List<OrderResponseDto> getMyOrders(Long profileId) {
        // 1. 혹시 모를 유령 사용자 방지
        memberProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 저장소에서 이 사용자의 주문 내역을 최신순으로 다 가져오기
        List<Order> orders = orderRepository.findAllByMemberProfileIdOrderByCreatedAtDesc(profileId);

        // 3. 예쁜 DTO 형태로 변환해서 리스트로 반환
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

}