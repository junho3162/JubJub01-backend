package org.example.jubjub.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto { // 👈 이 이름이 파일명이랑 똑같아야 합니다!
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
}