package org.example.jubjub.domain.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.cart.dto.CartRequestDto;
import org.example.jubjub.domain.cart.dto.CartResponseDto;
import org.example.jubjub.domain.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart", description = "장바구니 API")
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 메뉴 추가", description = "사용자의 장바구니에 메뉴를 담습니다.")
    @PostMapping //("/{profileId}")
    public ResponseEntity<String> addToCart(@AuthenticationPrincipal Long memberId, // 👈 토큰에서 내 번호를 알아서 꺼내옵니다!
                                            // @PathVariable Long profileId,
                                            @RequestBody CartRequestDto request) {
        cartService.addToCart(/*profileId,*/ memberId, request);
        return ResponseEntity.ok("장바구니에 성공적으로 담겼습니다! \uD83D\uDED2");
    }

    @Operation(summary = "내 장바구니 조회", description = "현재 사용자의 장바구니 목록을 조회합니다.")
    @GetMapping //("/{profileId}")
    public ResponseEntity<List<CartResponseDto>> getMyCart(//@PathVariable Long profileId,
                                                           @AuthenticationPrincipal Long memberId) { // 👈 토큰에서 내 번호를 꺼내옵니다!
        return ResponseEntity.ok(cartService.getMyCart(memberId /*, profileId*/));
    }

    @Operation(summary = "장바구니 특정 메뉴 삭제", description = "장바구니에서 특정 메뉴 하나를 뺍니다.")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCartItem(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long cartId) {
        cartService.deleteCartItem(memberId, cartId);
        return ResponseEntity.ok("메뉴가 장바구니에서 삭제되었습니다. 🗑️");
    }

    @Operation(summary = "장바구니 전체 비우기", description = "장바구니에 담긴 모든 메뉴를 싹 비웁니다.")
    @DeleteMapping
    public ResponseEntity<String> clearCart(
            @AuthenticationPrincipal Long memberId) {
        cartService.clearCart(memberId);
        return ResponseEntity.ok("장바구니가 싹 비워졌습니다! ✨");
    }
}