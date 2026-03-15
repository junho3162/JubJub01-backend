package org.example.jubjub.domain.store.repository;

import org.example.jubjub.domain.cart.entity.Cart;
import org.example.jubjub.domain.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // 활성화된 카테고리를 순서대로 조회하는 메서드 추가
    List<Category> findAllByIsActiveTrueOrderByDisplayOrderAsc();
}