package org.example.jubjub.domain.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.store.dto.StoreDetailResponseDto;
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

    @Operation(summary = "매장 상세 조회", description = "매장의 상세 정보와 메뉴 목록을 함께 조회합니다.")
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailResponseDto> getStoreDetail(@PathVariable Long storeId) {
        StoreDetailResponseDto storeDetail = storeService.getStoreDetail(storeId);
        return ResponseEntity.ok(storeDetail);
    }
}