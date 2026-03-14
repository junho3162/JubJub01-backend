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
}