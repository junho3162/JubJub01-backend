package org.example.jubjub.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken; // 발급된 JWT 토큰
    private String tokenType;   // 토큰 타입 (보통 "Bearer" 사용)
    private Long memberId;      // 로그인한 사용자의 PK
}