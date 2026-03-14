package org.example.jubjub.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.global.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber; // 주문표시번호 (예: ORD-20240314-X123)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id", nullable = false)
    private MemberProfile memberProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer totalAmount;      // 총 주문금액
    private Integer finalPaymentAmount; // 최종 결제 금액

    @Builder.Default
    private Boolean isEcoContainer = false; // 에코용기 지참 여부

    private LocalDateTime estimatedArrivalTime; // 고객 도착 예정 시간
    private LocalDateTime aiPickupTime;       // AI 추천 픽업 시간

    public enum OrderStatus {
        PENDING, ACCEPTED, PREPARING, READY, COMPLETED, CANCELLED
    }
}