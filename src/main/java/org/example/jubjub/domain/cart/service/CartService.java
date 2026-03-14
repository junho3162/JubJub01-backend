package org.example.jubjub.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.cart.dto.CartRequestDto;
import org.example.jubjub.domain.cart.dto.CartResponseDto;
import org.example.jubjub.domain.cart.entity.Cart;
import org.example.jubjub.domain.cart.repository.CartRepository;
import org.example.jubjub.domain.store.entity.Menu;
import org.example.jubjub.domain.store.repository.MenuRepository;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.domain.user.repository.MemberProfileRepository; // 레포지토리 필요
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final MemberProfileRepository memberProfileRepository;

    public void addToCart(Long memberId /*profileId*/, CartRequestDto request) {
        MemberProfile profile = memberProfileRepository.findByMemberId(memberId /*profileId*/)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Long profileId = profile.getId();

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

        // 👇  픽업 앱 핵심 원칙: 다른 매장 메뉴 섞임 방지
        cartRepository.findFirstByMemberProfileId(profileId).ifPresent(existingCart -> {
            if (!existingCart.getStore().getId().equals(menu.getStore().getId())) {
                throw new IllegalArgumentException("다른 매장의 메뉴는 담을 수 없습니다. 기존 장바구니를 비워주세요.");
            }
        });

        // 이미 장바구니에 있다면 수량 추가, 없으면 새로 생성
        cartRepository.findByMemberProfileIdAndMenuId(profileId, request.getMenuId())
                .ifPresentOrElse(
                        cart -> cart.updateQuantity(cart.getQuantity() + request.getQuantity()), // 엔티티에 메서드 추가 필요
                        () -> cartRepository.save(Cart.builder()
                                .memberProfile(profile)
                                .store(menu.getStore())
                                .menu(menu)
                                .quantity(request.getQuantity())
                                .build())
                );
    }

    // getMyCart 메서드도 동일하게 적용
    @Transactional(readOnly = true)
    public List<CartResponseDto> getMyCart(Long memberId) {
        MemberProfile profile = memberProfileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return cartRepository.findAllByMemberProfileId(profile.getId())
                .stream()
                .map(CartResponseDto::new)
                .collect(Collectors.toList());
    }
}