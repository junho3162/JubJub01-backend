package org.example.jubjub.domain.cart.dto;

import lombok.Getter;
import org.example.jubjub.domain.cart.entity.Cart;

@Getter
public class CartResponseDto {
    private final Long cartId;
    private final String menuName;
    private final Integer price;
    private final Integer quantity;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getId();
        this.menuName = cart.getMenu().getName();
        this.price = cart.getMenu().getPrice();
        this.quantity = cart.getQuantity();
    }
}
