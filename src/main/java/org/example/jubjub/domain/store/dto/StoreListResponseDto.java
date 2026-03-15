package org.example.jubjub.domain.store.dto;

import lombok.Getter;
import org.example.jubjub.domain.store.entity.Store;

@Getter
public class StoreListResponseDto {
    private final Long id;
    private final String name;
    private final String address;
    private final Integer baseCookingTimeMin;
    private final String status;

    public StoreListResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.baseCookingTimeMin = store.getBaseCookingTimeMin();
        this.status = store.getStatus().name();
    }
}