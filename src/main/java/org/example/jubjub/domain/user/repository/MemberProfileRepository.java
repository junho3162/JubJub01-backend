package org.example.jubjub.domain.user.repository;

import org.example.jubjub.domain.user.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    // 통합계정 ID로 프로필 찾기 (나중에 JWT 토큰에서 꺼내 쓸 때 필요)
    Optional<MemberProfile> findByMemberId(Long memberId);

    // 전화번호 중복 검사용 (고객_프로필 ERD에 전화번호 unique 제약이 있음)
    boolean existsByPhone(String phone);
}