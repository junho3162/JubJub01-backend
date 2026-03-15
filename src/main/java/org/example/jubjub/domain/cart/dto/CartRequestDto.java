package org.example.jubjub.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequestDto {
    private Long menuId;
    private Integer quantity;
}