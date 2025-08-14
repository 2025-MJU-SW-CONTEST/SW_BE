package com.example.sw_be.domain.movie.dto;

import com.example.sw_be.domain.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MovieDetailResponse {

    private Long id;
    private String title;
    private Float rating;
    private String thumbnailUrl;
    private String summary;
    private LocalDate releaseDate;

    public static MovieDetailResponse from(Movie movie) {
        return MovieDetailResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating())
                .thumbnailUrl(movie.getThumbnailUrl())
                .summary(movie.getSummary())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}