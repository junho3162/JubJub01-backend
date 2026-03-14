package org.example.jubjub.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.auth.entity.AuthLog;
import org.example.jubjub.domain.auth.entity.Member;
import org.example.jubjub.domain.auth.repository.AuthLogRepository;
import org.example.jubjub.domain.auth.repository.MemberRepository;
import org.example.jubjub.domain.auth.dto.AuthCodeRequestDto;
import org.example.jubjub.domain.auth.dto.AuthVerifyRequestDto;
import org.example.jubjub.domain.auth.dto.SignupRequestDto;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.domain.user.repository.MemberProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.jubjub.domain.auth.dto.LoginRequestDto;
import org.example.jubjub.domain.auth.dto.LoginResponseDto;
import org.example.jubjub.domain.auth.jwt.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthLogRepository authLogRepository;
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final PasswordEncoder passwordEncoder;

    // 👇 방금 만든 토큰 발급기를 서비스로 불러옵니다! (추가)
    private final JwtTokenProvider jwtTokenProvider;

    // 1. 인증번호 발송
    public String sendAuthCode(AuthCodeRequestDto request) {
        String randomCode = String.format("%06d", new Random().nextInt(1000000));

        AuthLog authLog = AuthLog.builder()
                .authType(request.getAuthType().toUpperCase())
                .target(request.getTarget())
                .authCode(randomCode)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        authLogRepository.save(authLog);

        System.out.println("\n💌 [" + authLog.getAuthType() + " 발송] 대상: " + authLog.getTarget());
        System.out.println("🔑 인증번호: [" + authLog.getAuthCode() + "]\n");

        return request.getAuthType() + "로 인증번호가 발송되었습니다.";
    }

    // 2. 인증번호 검증
    public String verifyAuthCode(AuthVerifyRequestDto request) {
        AuthLog authLog = authLogRepository.findTopByTargetAndExpiresAtAfterOrderByCreatedAtDesc(
                request.getTarget(), LocalDateTime.now()
        ).orElseThrow(() -> new IllegalArgumentException("만료되었거나 존재하지 않는 인증 요청입니다."));

        if (!authLog.getAuthCode().equals(request.getAuthCode())) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        authLog.verifySuccess();
        return "본인인증이 완료되었습니다.";
    }

    // 3. 회원가입
    @Transactional
    public String signup(SignupRequestDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (memberProfileRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }

        // 인증 여부 확인 (최근 1시간 내 인증 성공 기록 대상)
        AuthLog authLog = authLogRepository.findTopByTargetAndExpiresAtAfterOrderByCreatedAtDesc(
                request.getPhone(), LocalDateTime.now().minusHours(1)
        ).orElseThrow(() -> new IllegalArgumentException("본인인증 기록이 없습니다."));

        if (!authLog.getIsVerified()) {
            throw new IllegalArgumentException("본인인증이 완료되지 않았습니다.");
        }

        // 통합계정 생성
        Member member = Member.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                // ⚠️ .role("USER") 대신 아래처럼 Enum 타입을 직접 넣어줍니다!
                .role(Member.MemberRole.USER)
                .status(Member.MemberStatus.ACTIVE)
                .build();
        Member savedMember = memberRepository.save(member);

        // 고객 프로필 생성
        MemberProfile profile = MemberProfile.builder()
                .member(savedMember)
                .name(request.getName())
                .phone(request.getPhone())
                .nickname(request.getNickname())
                // [수정 포인트 3] MemberProfile 엔티티에 정의된 필드만 입력 (email 제외) .email(request.getEmail())
                .build();
        memberProfileRepository.save(profile);

        return "회원가입이 완료되었습니다! 환영합니다, " + request.getName() + "님!";
    }

    // 4. 로그인 및 JWT 토큰 발급 로직 (새로 추가!)
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {
        // 1. 이메일로 회원 찾기
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호가 맞는지 확인 (암호화된 비밀번호와 비교)
        // 주의: passwordEncoder.matches(입력한 생짜 비번, DB에 저장된 암호화 비번) 순서여야 합니다!
        if (!passwordEncoder.matches(request.getPassword(), member.getPasswordHash())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 이메일과 비밀번호가 모두 맞다면? -> 출입증(토큰) 발급!
        String token = jwtTokenProvider.createToken(
                member.getId(),
                member.getEmail(),
                member.getRole().name()
        );

        // 4. 발급된 토큰을 바구니에 담아서 반환
        return new LoginResponseDto(token, "Bearer", member.getId());
    }
}