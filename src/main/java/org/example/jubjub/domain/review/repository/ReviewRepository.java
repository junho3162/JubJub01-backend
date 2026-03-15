package org.example.jubjub.domain.review.repository;

import org.example.jubjub.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 1. 이 주문으로 이미 리뷰를 썼는지 확인 (중복 방지용)
    boolean existsByOrderId(Long orderId);

    // 2. 특정 매장의 리뷰 목록을 최신순으로 조회 (다른 손님들이 볼 때 사용)
    List<Review> findAllByStoreIdOrderByCreatedAtDesc(Long storeId);
}