package org.example.jubjub.domain.review.dto;

import lombok.Getter;
import org.example.jubjub.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private final Long reviewId;
    private final String nickname; // 리뷰 작성자 닉네임
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.nickname = review.getMemberProfile().getNickname();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}