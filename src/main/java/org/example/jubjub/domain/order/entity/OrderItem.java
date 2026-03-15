package org.example.jubjub.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.store.entity.Menu;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer orderPrice; // 주문 당시 가격 (가격 변동 대비)

    @Column(nullable = false)
    private Integer count; // 주문 수량
}