package org.example.jubjub.domain.auth.repository;

import org.example.jubjub.domain.auth.entity.AuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthLogRepository extends JpaRepository<AuthLog, Long> {
    // 특정 번호/이메일로 보낸 아직 만료되지 않은 최신 인증코드 조회
    Optional<AuthLog> findTopByTargetAndExpiresAtAfterOrderByCreatedAtDesc(String target, LocalDateTime now);
}