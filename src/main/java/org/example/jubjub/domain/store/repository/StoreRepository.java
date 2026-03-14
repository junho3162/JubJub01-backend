package org.example.jubjub.domain.store.repository;

import org.example.jubjub.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    // 카테고리 ID로 매장 목록 조회
    List<Store> findAllByCategoryId(Integer categoryId);
}