package org.example.jubjub.domain.favorite.dto;

import lombok.Getter;
import org.example.jubjub.domain.favorite.entity.StoreFavorite;

import java.time.LocalDateTime;

@Getter
public class StoreFavoriteResponseDto {
    private final Long favoriteId;
    private final Long storeId;
    private final String storeName;
    private final LocalDateTime favoritedAt; // 찜한 시간

    public StoreFavoriteResponseDto(StoreFavorite favorite) {
        this.favoriteId = favorite.getId();
        this.storeId = favorite.getStore().getId();
        this.storeName = favorite.getStore().getName();
        this.favoritedAt = favorite.getCreatedAt();
    }
}