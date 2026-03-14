package org.example.jubjub.domain.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.store.entity.Menu;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id", nullable = false)
    private MemberProfile memberProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Builder.Default
    private Integer quantity = 1; // 수량

    // === 아래 비즈니스 로직 메서드를 추가했습니다 === //

    /**
     * 장바구니 수량 업데이트
     * 기존 수량에 더하거나 새로운 수량으로 변경할 때 사용합니다.
     */
    public void updateQuantity(Integer newQuantity) {
        this.quantity = newQuantity;
    }
}