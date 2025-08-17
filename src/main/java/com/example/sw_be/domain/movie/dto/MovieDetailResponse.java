package com.example.sw_be.domain.movie.dto;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movieCast.dto.MovieCastsResponse;
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
    private MovieCastsResponse movieCasts;

    public static MovieDetailResponse from(Movie movie, MovieCastsResponse movieCastsResponse) {
        return MovieDetailResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating())
                .thumbnailUrl(movie.getThumbnailUrl())
                .summary(movie.getSummary())
                .releaseDate(movie.getReleaseDate())
                .movieCasts(movieCastsResponse)
                .build();
    }
}
