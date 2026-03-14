package org.example.jubjub.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.jubjub.domain.review.dto.ReviewCreateRequestDto;
import org.example.jubjub.domain.review.dto.ReviewResponseDto;
import org.example.jubjub.domain.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review", description = "리뷰 API")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성", description = "완료된 주문에 대해 리뷰를 작성합니다.")
    @PostMapping("/{profileId}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long profileId,
            @RequestBody ReviewCreateRequestDto request) {

        ReviewResponseDto response = reviewService.createReview(profileId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "매장별 리뷰 조회", description = "특정 매장의 모든 리뷰를 최신순으로 조회합니다.")
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> getStoreReviews(@PathVariable Long storeId) {

        return ResponseEntity.ok(reviewService.getStoreReviews(storeId));
    }
}