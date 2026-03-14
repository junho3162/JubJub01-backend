package org.example.jubjub.global.config;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.auth.jwt.JwtAuthenticationFilter; // 👈 필터 임포트 추가
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // 👈 의존성 주입을 위해
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 방금 보신 그 기본 로그인 화면 끄기!
                .formLogin(AbstractHttpConfigurer::disable)
                // 2. HTTP Basic 인증 끄기
                .httpBasic(AbstractHttpConfigurer::disable)
                // 3. JWT를 사용할 것이므로 CSRF 방어기능 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // 4. 세션(Session) 대신 토큰(Token)을 쓸 거니까 상태를 Stateless로 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 5. 길 열어주기!
                .authorizeHttpRequests(auth -> auth
                        // 스웨거 화면과 회원가입/인증 API는 누구든 들어올 수 있게 허락합니다.
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // 🚨 나머지 API(장바구니, 주문 등)는 일단 지금은 다 열어둡니다! (나중에 JWT 필터 만들 때 닫을 거예요)
                        // 👇 이 부분이 반드시 authenticated() 여야 합니다! (permitAll 이면 안 돼요!)
                        .anyRequest().authenticated()
                )
                // 👇 비밀번호 검사하기 전에 우리가 만든 JWT 검문소부터 거치도록 설정!
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 보너스: 회원가입할 때 비밀번호를 안전하게 암호화해줄 도구입니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}