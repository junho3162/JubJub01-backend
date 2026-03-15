package org.example.jubjub.domain.store.dto;

import lombok.Getter;
import org.example.jubjub.domain.store.entity.Store;
import java.util.List;

@Getter
public class StoreDetailResponseDto {
    private final Long id;
    private final String name;
    private final String address;
    private final String phone;
    private final String originInfo;
    private final List<MenuResponseDto> menus;

    // 서비스에서 바로 사용할 수 있는 생성자
    public StoreDetailResponseDto(Store store, List<MenuResponseDto> menus) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.phone = store.getPhone();
        this.originInfo = store.getOriginInfo();
        this.menus = menus;
    }
}