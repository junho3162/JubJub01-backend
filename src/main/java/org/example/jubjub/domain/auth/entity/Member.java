package org.example.jubjub.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "members") // 테이블명은 관례상 복수형 또는 통합계정 사용
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberRole role = MemberRole.USER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberStatus status = MemberStatus.ACTIVE;

    // Enum 타입 정의 (가독성을 위해 내부에 정의하거나 별도 파일로 분리)
    public enum MemberRole { USER, ADMIN }
    public enum MemberStatus { ACTIVE, DELETED, BANNED }
}