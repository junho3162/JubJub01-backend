package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

// BaseTimeEntity는 매핑 테이블 성격상 생략해도 되지만, 추적을 위해 상속받아도 무방합니다.
@Entity
@Table(
        name = "menu_option_mappings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_menu_option_group", // 중복 매핑 방지
                        columnNames = {"menu_id", "option_group_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MenuOptionMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private OptionGroup optionGroup;
}