package org.example.jubjub.domain.cart.repository;

import org.example.jubjub.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 1. 내 장바구니 목록 전체 조회
    List<Cart> findAllByMemberProfileId(Long memberProfileId);

    // 2. 장바구니에 이미 똑같은 메뉴가 담겨있는지 확인 (수량 증가용)
    Optional<Cart> findByMemberProfileIdAndMenuId(Long memberProfileId, Long menuId);

    // 3. 내 장바구니에 담긴 아무 아이템이나 하나 가져오기 (어느 매장인지 검사하기 위함)
    Optional<Cart> findFirstByMemberProfileId(Long memberProfileId);

    // 4. 내 장바구니 싹 비우기 (다른 매장 메뉴 담을 때 호출)
    void deleteAllByMemberProfileId(Long memberProfileId);
}