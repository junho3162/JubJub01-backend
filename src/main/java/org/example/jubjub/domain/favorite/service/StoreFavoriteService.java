package org.example.jubjub.domain.favorite.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.favorite.dto.StoreFavoriteResponseDto;
import org.example.jubjub.domain.favorite.entity.StoreFavorite;
import org.example.jubjub.domain.favorite.repository.StoreFavoriteRepository;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.store.repository.StoreRepository;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.domain.user.repository.MemberProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreFavoriteService {

    private final StoreFavoriteRepository favoriteRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final StoreRepository storeRepository;

    // 찜하기 등록/취소 토글 (Toggle)
    public String toggleFavorite(Long profileId, Long storeId) {
        // 1. 회원 및 매장 검증
        MemberProfile profile = memberProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        // 2. 이미 찜한 기록이 있는지 확인
        Optional<StoreFavorite> existingFavorite = favoriteRepository.findByMemberProfileIdAndStoreId(profileId, storeId);

        if (existingFavorite.isPresent()) {
            // 3-1. 기록이 있으면? -> 찜 취소 (삭제)
            favoriteRepository.delete(existingFavorite.get());
            return "매장 찜하기가 취소되었습니다. 💔";
        } else {
            // 3-2. 기록이 없으면? -> 찜 등록 (저장)
            StoreFavorite favorite = StoreFavorite.builder()
                    .memberProfile(profile)
                    .store(store)
                    .build();
            favoriteRepository.save(favorite);
            return "매장 찜하기가 완료되었습니다. ❤️";
        }
    }

    // 내 찜 목록 조회
    @Transactional(readOnly = true)
    public List<StoreFavoriteResponseDto> getMyFavorites(Long profileId) {
        // 유효한 사용자인지 먼저 확인
        memberProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 내 찜 목록을 가져와서 DTO로 변환 후 반환
        return favoriteRepository.findAllByMemberProfileIdOrderByCreatedAtDesc(profileId)
                .stream()
                .map(StoreFavoriteResponseDto::new)
                .collect(Collectors.toList());
    }
}