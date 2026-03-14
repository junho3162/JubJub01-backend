package org.example.jubjub.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.auth.entity.Member;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "member_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    private String nickname;

    @Builder.Default
    private Integer totalWalkingDistance = 0;

    @Builder.Default
    private Integer accumulatedExp = 0;

    @Builder.Default
    private Boolean pushAgree = true;

    // ERD에 정의된 나머지 필드들도 동일한 방식으로 추가 가능합니다.
}