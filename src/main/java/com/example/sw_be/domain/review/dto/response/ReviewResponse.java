package com.example.sw_be.domain.review.dto.response;

import com.example.sw_be.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {
    private Long id;
    private String content;
    private Float rating;
    private LocalDateTime createdAt;
    private long movie_id;

    public ReviewResponse(Review review){
        this.id= review.getId();
        this.content= review.getContent();
        this.rating= review.getRating();
        this.createdAt= review.getCreatedAt();
        this.movie_id= review.getMovie().getId();
    }
}
