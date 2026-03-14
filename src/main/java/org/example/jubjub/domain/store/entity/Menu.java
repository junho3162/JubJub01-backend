package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    // 메뉴(N) : 매장(1) 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name; // 메뉴명

    @Column(nullable = false)
    private Integer price; // 가격

    @Column(columnDefinition = "TEXT")
    private String description; // 메뉴 설명

    @Builder.Default
    private Boolean isSoldOut = false; // 품절 여부
}