package org.example.jubjub.domain.order.repository;

import org.example.jubjub.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 특정 사용자의 주문 내역을 최신순으로 조회
    List<Order> findAllByMemberProfileIdOrderByCreatedAtDesc(Long memberProfileId);
}