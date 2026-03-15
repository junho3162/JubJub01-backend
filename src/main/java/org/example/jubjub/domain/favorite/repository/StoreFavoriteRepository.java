package org.example.jubjub.domain.favorite.repository;

import org.example.jubjub.domain.favorite.entity.StoreFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreFavoriteRepository extends JpaRepository<StoreFavorite, Long> {

    // 1. 이미 찜한 매장인지 확인 (찜 토글 기능을 위해 필요)
    Optional<StoreFavorite> findByMemberProfileIdAndStoreId(Long profileId, Long storeId);

    // 2. 내 찜 목록 불러오기 (최근에 찜한 순서대로)
    List<StoreFavorite> findAllByMemberProfileIdOrderByCreatedAtDesc(Long profileId);
}