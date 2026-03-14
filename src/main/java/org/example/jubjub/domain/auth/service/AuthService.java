package org.example.jubjub.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.auth.entity.AuthLog;
import org.example.jubjub.domain.auth.repository.AuthLogRepository;
import org.example.jubjub.domain.auth.dto.AuthCodeRequestDto;
import org.example.jubjub.domain.auth.dto.AuthVerifyRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthLogRepository authLogRepository;

    // 1. 인증번호 6자리 생성 및 발송 (시뮬레이션)
    public String sendAuthCode(AuthCodeRequestDto request) {
        // 무작위 6자리 숫자 생성 (000000 ~ 999999)
        String randomCode = String.format("%06d", new Random().nextInt(1000000));

        // DB에 인증 내역 저장 (유효기간 5분)
        AuthLog authLog = AuthLog.builder()
                .authType(request.getAuthType().toUpperCase())
                .target(request.getTarget())
                .authCode(randomCode)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        authLogRepository.save(authLog);

        // 🚨 실제 발송 대신 서버 콘솔에 출력 (나중에 진짜 API로 교체할 부분)
        System.out.println("\n========================================");
        System.out.println("💌 [" + authLog.getAuthType() + " 발송] 대상: " + authLog.getTarget());
        System.out.println("🔑 인증번호: [" + authLog.getAuthCode() + "] (5분 내에 입력해주세요.)");
        System.out.println("========================================\n");

        return request.getAuthType() + "로 인증번호가 발송되었습니다. (유효시간 5분)";
    }

    // 2. 사용자가 입력한 인증번호 검증
    public String verifyAuthCode(AuthVerifyRequestDto request) {
        // 발송 대상과 일치하면서 아직 5분이 안 지난 최신 인증기록 찾기
        AuthLog authLog = authLogRepository.findTopByTargetAndExpiresAtAfterOrderByCreatedAtDesc(
                request.getTarget(), LocalDateTime.now()
        ).orElseThrow(() -> new IllegalArgumentException("만료되었거나 존재하지 않는 인증 요청입니다. 다시 발송해주세요."));

        // 인증번호 일치 여부 확인
        if (!authLog.getAuthCode().equals(request.getAuthCode())) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다. 다시 확인해주세요.");
        }

        // 일치한다면 인증 성공 처리! (isVerified = true)
        authLog.verifySuccess();

        return "본인인증이 성공적으로 완료되었습니다.";
    }
}