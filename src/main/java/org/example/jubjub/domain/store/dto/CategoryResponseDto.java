package org.example.jubjub.domain.store.dto;

import lombok.Getter;
import org.example.jubjub.domain.store.entity.Category;

@Getter
public class CategoryResponseDto {
    private final Integer id;
    private final String name;
    private final String iconUrl;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.iconUrl = category.getIconUrl();
    }
}