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
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        String url = baseUrl + movie.getThumbnailUrl();
        return MovieDetailResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating())
                .thumbnailUrl(url)
                .summary(movie.getSummary())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}
