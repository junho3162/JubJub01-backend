package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id; // ERD에서 int로 설계하셨으므로 Integer 사용

    @Column(nullable = false)
    private String name; // 카테고리명 (예: 한식, 카페)

    private String iconUrl; // 아이콘 이미지 주소

    @Builder.Default
    private Integer displayOrder = 0; // 노출 순서 (숫자가 작을수록 상단 노출)

    @Builder.Default
    private Boolean isActive = true; // 활성 여부 (false면 앱에 안 보임)
}