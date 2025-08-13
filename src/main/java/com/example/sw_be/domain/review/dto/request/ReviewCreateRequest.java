package com.example.sw_be.domain.review.dto.request;

import com.example.sw_be.domain.movie.entity.Movie;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewCreateRequest {

    private String content;
    private Float rating;
    private long movie_id;
}
