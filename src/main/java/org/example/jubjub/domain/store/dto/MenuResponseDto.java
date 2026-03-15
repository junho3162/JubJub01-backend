package org.example.jubjub.domain.store.dto;

import lombok.Getter;
import org.example.jubjub.domain.store.entity.Menu;

@Getter
public class MenuResponseDto {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;
    private final Boolean isSoldOut;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.isSoldOut = menu.getIsSoldOut();
    }
}