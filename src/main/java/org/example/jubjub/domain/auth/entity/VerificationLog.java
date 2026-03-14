package org.example.jubjub.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VerificationLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationType type; // SMS, EMAIL

    @Column(nullable = false)
    private String target; // 전화번호 또는 이메일 주소

    private String requestType; // 비밀번호찾기, 회원가입 등

    @Column(nullable = false)
    private String verificationCode; // 6자리 난수

    @Builder.Default
    private Boolean isVerified = false; // 인증 완료 여부

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료 일시

    // Enum 정의
    public enum VerificationType { SMS, EMAIL }

    /**
     * 비즈니스 로직: 인증 코드가 만료되었는지 확인하는 메서드
     */
    public boolean isExpired(LocalDateTime now) {
        return now.isAfter(this.expiresAt);
    }

    /**
     * 비즈니스 로직: 인증 성공 처리
     */
    public void verify() {
        this.isVerified = true;
    }
}