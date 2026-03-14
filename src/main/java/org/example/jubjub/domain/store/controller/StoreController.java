package org.example.jubjub.domain.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.store.dto.StoreListResponseDto;
import org.example.jubjub.domain.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Store", description = "매장 관련 API")
@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "카테고리별 매장 목록 조회", description = "특정 카테고리에 속한 모든 매장을 조회합니다.")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<StoreListResponseDto>> getStoresByCategory(@PathVariable Integer categoryId) {
        List<StoreListResponseDto> stores = storeService.getStoresByCategory(categoryId);
        return ResponseEntity.ok(stores);
    }
}