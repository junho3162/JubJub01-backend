package org.example.jubjub.domain.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long expirationTime;

    // application.yml에 적어둔 설정값을 가져와서 기계 세팅하기
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-time}") long expirationTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationTime = expirationTime;
    }

    // 1. 토큰(출입증) 생성 메서드
    public String createToken(Long memberId, String email, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime); // 현재 시간 + 24시간

        return Jwts.builder()
                .subject(email) // 토큰의 주인 (이메일)
                .claim("memberId", memberId) // 추가 정보: 회원 PK 번호
                .claim("role", role) // 추가 정보: 권한 (USER / ADMIN)
                .issuedAt(now) // 발급 시간
                .expiration(validity) // 만료 시간
                .signWith(key) // 우리 서버만의 비밀키로 서명(도장 쾅!)
                .compact(); // 토큰 문자열로 압축!
    }
}