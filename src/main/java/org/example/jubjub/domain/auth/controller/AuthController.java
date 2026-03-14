package org.example.jubjub.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.auth.dto.AuthCodeRequestDto;
import org.example.jubjub.domain.auth.dto.AuthVerifyRequestDto;
import org.example.jubjub.domain.auth.dto.SignupRequestDto; // 추가됨
import org.example.jubjub.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.jubjub.domain.auth.dto.LoginRequestDto;
import org.example.jubjub.domain.auth.dto.LoginResponseDto;

@Tag(name = "Auth", description = "회원가입, 로그인 및 본인인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "인증번호 발송 요청")
    @PostMapping("/send-code")
    public ResponseEntity<String> sendAuthCode(@RequestBody AuthCodeRequestDto request) {
        return ResponseEntity.ok(authService.sendAuthCode(request));
    }

    @Operation(summary = "인증번호 검증")
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyAuthCode(@RequestBody AuthVerifyRequestDto request) {
        return ResponseEntity.ok(authService.verifyAuthCode(request));
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}