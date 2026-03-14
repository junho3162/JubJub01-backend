package org.example.jubjub.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.store.dto.MenuResponseDto;
import org.example.jubjub.domain.store.dto.StoreDetailResponseDto;
import org.example.jubjub.domain.store.dto.StoreListResponseDto;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.store.repository.MenuRepository;
import org.example.jubjub.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository; // 추가됨

    /**
     * 카테고리별 매장 목록 조회
     */
    public List<StoreListResponseDto> getStoresByCategory(Integer categoryId) {
        return storeRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(StoreListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 매장 상세 조회 (매장 정보 + 메뉴 목록)
     */
    public StoreDetailResponseDto getStoreDetail(Long storeId) {
        // 1. 매장 엔티티 조회 (없으면 예외 발생)
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

        // 2. 해당 매장의 메뉴 목록 조회 및 DTO 변환
        List<MenuResponseDto> menus = menuRepository.findAllByStoreId(storeId)
                .stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());

        // 3. 매장 정보와 메뉴 목록을 결합하여 반환
        return new StoreDetailResponseDto(store, menus);
    }
}