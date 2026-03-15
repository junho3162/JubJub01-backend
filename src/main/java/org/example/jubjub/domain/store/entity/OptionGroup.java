package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "option_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OptionGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_id")
    private Long id;

    // 옵션그룹(N) : 매장(1) 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name; // 그룹명 (예: 사이즈 선택, 소스 추가)

    @Builder.Default
    private Boolean isRequired = false; // 필수 선택 여부

    @Builder.Default
    private Boolean isMultiple = false; // 다중 선택 가능 여부
}