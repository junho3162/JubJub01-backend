package org.example.jubjub.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.global.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuthLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_log_id")
    private Long id;

    @Column(nullable = false)
    private String authType; // "SMS" 또는 "EMAIL"

    @Column(nullable = false)
    private String target; // 발송 대상 (예: "01012345678" 또는 "test@naver.com")

    private String requestType; // "SIGNUP", "PASSWORD_FIND" 등

    @Column(nullable = false, length = 10)
    private String authCode; // 발송된 6자리 난수

    @Column(nullable = false)
    @Builder.Default
    private Boolean isVerified = false; // 인증 완료 여부

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료 시간 (보통 생성 후 3~5분)

    // 인증 성공 처리 메서드
    public void verifySuccess() {
        this.isVerified = true;
    }
}