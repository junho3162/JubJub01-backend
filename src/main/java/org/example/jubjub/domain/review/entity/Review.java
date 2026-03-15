package org.example.jubjub.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.order.entity.Order;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id", nullable = false)
    private MemberProfile memberProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 한 주문당 리뷰는 1개만! (unique = true 추가)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private Integer rating; // 별점 (1~5)

    @Column(columnDefinition = "TEXT")
    private String content; // 리뷰 내용

    private String imageUrl; // 리뷰 사진

    @Column(columnDefinition = "TEXT")
    private String ownerReply; // 사장님 답글
}