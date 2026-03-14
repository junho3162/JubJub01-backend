package org.example.jubjub.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequestDto {
    private Long orderId;    // 어떤 주문에 대한 리뷰인지
    private Integer rating;  // 별점 (1~5)
    private String content;  // 리뷰 내용
}