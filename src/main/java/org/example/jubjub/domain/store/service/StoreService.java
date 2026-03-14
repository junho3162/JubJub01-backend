package org.example.jubjub.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.store.dto.StoreListResponseDto;
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

    public List<StoreListResponseDto> getStoresByCategory(Integer categoryId) {
        return storeRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(StoreListResponseDto::new)
                .collect(Collectors.toList());
    }
}