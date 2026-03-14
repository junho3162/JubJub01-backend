package org.example.jubjub.domain.cart.repository;

import org.example.jubjub.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 특정 사용자의 장바구니 목록 조회
    List<Cart> findAllByMemberProfileId(Long memberProfileId);

    // 이 메서드가 여기에 있어야 합니다!
    Optional<Cart> findByMemberProfileIdAndMenuId(Long memberProfileId, Long menuId);
}