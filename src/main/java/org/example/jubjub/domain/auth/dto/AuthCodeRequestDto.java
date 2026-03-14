package org.example.jubjub.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeRequestDto {
    private String authType; // "SMS" 또는 "EMAIL"
    private String target;   // 전화번호(01012345678) 또는 이메일(test@example.com)
}