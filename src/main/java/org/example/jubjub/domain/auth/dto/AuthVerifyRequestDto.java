package org.example.jubjub.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthVerifyRequestDto {
    private String authType; // "SMS" 또는 "EMAIL"
    private String target;   // 전화번호 또는 이메일
    private String authCode; // 손님이 입력한 6자리 숫자 (예: "492811")
}