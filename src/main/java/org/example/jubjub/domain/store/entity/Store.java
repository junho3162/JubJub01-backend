package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    // 매장(N) : 카테고리(1) 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name; // 매장명

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String phone; // 전화번호

    // 위치 기반 검색을 위한 위도, 경도
    private Double latitude;
    private Double longitude;

    @Builder.Default
    private Integer baseCookingTimeMin = 15; // 기본 조리 시간(분)

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StoreStatus status = StoreStatus.OPEN; // 영업 상태

    @Column(columnDefinition = "TEXT")
    private String originInfo; // 원산지 통합 정보

    // 영업 상태 관리를 위한 Enum
    public enum StoreStatus {
        OPEN, CLOSED, BREAK_TIME
    }
}