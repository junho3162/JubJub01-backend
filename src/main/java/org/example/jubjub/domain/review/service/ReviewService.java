package org.example.jubjub.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.order.entity.Order;
import org.example.jubjub.domain.order.repository.OrderRepository;
import org.example.jubjub.domain.review.dto.ReviewCreateRequestDto;
import org.example.jubjub.domain.review.dto.ReviewResponseDto;
import org.example.jubjub.domain.review.entity.Review;
import org.example.jubjub.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public ReviewResponseDto createReview(Long profileId, ReviewCreateRequestDto request) {
        // 주문 정보 가져오기
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문 내역을 찾을 수 없습니다."));

        // 🚨 원칙 1: 내 주문이 맞는가?
        if (!order.getMemberProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException("본인이 주문한 내역에만 리뷰를 작성할 수 있습니다.");
        }

        // 🚨 원칙 2: 완료된 상태(COMPLETED)인가?
        // (주의: OrderStatus enum에 COMPLETED가 있어야 합니다!)
        if (!order.getStatus().name().equals("COMPLETED")) {
            throw new IllegalArgumentException("배달/포장이 완료된 후에만 리뷰를 작성할 수 있습니다.");
        }

        // 🚨 원칙 3: 이미 리뷰를 쓰지는 않았는가?
        if (reviewRepository.existsByOrderId(order.getId())) {
            throw new IllegalArgumentException("이미 이 주문에 대한 리뷰를 작성하셨습니다.");
        }

        // 검증 통과! 리뷰 생성 및 저장
        Review review = Review.builder()
                .memberProfile(order.getMemberProfile())
                .store(order.getStore())
                .order(order)
                .rating(request.getRating())
                .content(request.getContent())
                // imageUrl이나 ownerReply는 나중에 별도 API로 추가/수정하도록 null로 둡니다.
                .build();

        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(savedReview);
    }

    // 매장별 리뷰 조회 (다른 손님들이 볼 수 있게)
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getStoreReviews(Long storeId) {
        return reviewRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId)
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }
}