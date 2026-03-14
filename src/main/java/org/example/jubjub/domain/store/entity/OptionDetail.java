package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "option_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OptionDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_detail_id")
    private Long id;

    // 옵션상세(N) : 옵션그룹(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private OptionGroup optionGroup;

    @Column(nullable = false)
    private String name; // 옵션명 (예: 덜 맵게, L 사이즈)

    @Builder.Default
    private Integer extraPrice = 0; // 추가 금액

    @Builder.Default
    private Boolean isSoldOut = false; // 품절 여부
}