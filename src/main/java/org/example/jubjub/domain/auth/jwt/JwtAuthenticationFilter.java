package org.example.jubjub.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 손님이 낸 헤더에서 "Bearer [토큰]" 꺼내기
        String bearerToken = request.getHeader("Authorization");

        // 2. 토큰이 있고, "Bearer "로 시작한다면 검사 시작!
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7); // "Bearer " 글자 떼어내고 순수 토큰만 추출

            // 3. 감식기로 진짜 토큰인지 확인
            if (jwtTokenProvider.validateToken(token)) {
                // 4. 진짜면? 손님 번호랑 권한 확인
                Long memberId = jwtTokenProvider.getMemberId(token);
                String role = jwtTokenProvider.getRole(token);

                // 5. 스프링 시큐리티한테 "이 손님 정상이다! 통과시켜라!" 라고 신분증(Authentication) 쥐어주기
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, null, List.of(authority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. 다음 목적지(API)로 보내주기
        filterChain.doFilter(request, response);
    }
}