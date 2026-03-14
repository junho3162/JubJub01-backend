package org.example.jubjub.domain.favorite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.favorite.dto.StoreFavoriteResponseDto;
import org.example.jubjub.domain.favorite.service.StoreFavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favorite", description = "단골 매장 찜하기 API")
@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class StoreFavoriteController {

    private final StoreFavoriteService favoriteService;

    @Operation(summary = "매장 찜하기 토글", description = "매장을 찜하거나 이미 찜한 매장이라면 찜을 취소합니다.")
    @PostMapping("/{profileId}/stores/{storeId}")
    public ResponseEntity<String> toggleFavorite(
            @PathVariable Long profileId,
            @PathVariable Long storeId) {

        String responseMessage = favoriteService.toggleFavorite(profileId, storeId);
        return ResponseEntity.ok(responseMessage);
    }

    @Operation(summary = "내 찜 목록 조회", description = "내가 찜한 매장 목록을 최신순으로 조회합니다.")
    @GetMapping("/{profileId}")
    public ResponseEntity<List<StoreFavoriteResponseDto>> getMyFavorites(
            @PathVariable Long profileId) {

        List<StoreFavoriteResponseDto> response = favoriteService.getMyFavorites(profileId);
        return ResponseEntity.ok(response);
    }
}