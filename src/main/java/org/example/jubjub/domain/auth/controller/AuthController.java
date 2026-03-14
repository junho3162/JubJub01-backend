package org.example.jubjub.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.auth.dto.AuthCodeRequestDto;
import org.example.jubjub.domain.auth.dto.AuthVerifyRequestDto;
import org.example.jubjub.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "회원가입, 로그인 및 본인인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "인증번호 발송 요청", description = "SMS 또는 이메일로 6자리 인증번호를 발송합니다.")
    @PostMapping("/send-code")
    public ResponseEntity<String> sendAuthCode(@RequestBody AuthCodeRequestDto request) {
        return ResponseEntity.ok(authService.sendAuthCode(request));
    }

    @Operation(summary = "인증번호 검증", description = "수신한 6자리 인증번호가 맞는지 검증합니다.")
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyAuthCode(@RequestBody AuthVerifyRequestDto request) {
        return ResponseEntity.ok(authService.verifyAuthCode(request));
    }
}