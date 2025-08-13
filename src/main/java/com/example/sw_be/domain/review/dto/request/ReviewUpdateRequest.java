package com.example.sw_be.domain.review.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewUpdateRequest {
    private Long id;
    private String content;
    private Float rating;
    private long movie_id;
}
