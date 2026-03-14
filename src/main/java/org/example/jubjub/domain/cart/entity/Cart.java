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
}