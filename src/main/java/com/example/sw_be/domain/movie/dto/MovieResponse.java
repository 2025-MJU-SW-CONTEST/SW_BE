package com.example.sw_be.domain.movie.dto;

import com.example.sw_be.domain.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieResponse {

    private Long id;
    private String title;
    private Float rating;
    private String thumbnailUrl;

    public static MovieResponse from(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating())
                .thumbnailUrl(movie.getThumbnailUrl())
                .build();
    }
}