package org.example.jubjub.domain.order.repository;

import org.example.jubjub.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 주문 상세 항목을 다룰 기본 레포지토리
}