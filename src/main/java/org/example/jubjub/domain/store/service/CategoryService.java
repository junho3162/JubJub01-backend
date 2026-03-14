package org.example.jubjub.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.store.dto.CategoryResponseDto;
import org.example.jubjub.domain.store.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllActiveCategories() {
        return categoryRepository.findAllByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}