package com.example.sw_be.domain.movie.dto;

import com.example.sw_be.domain.movie.entity.Movie;
import lombok.Getter;

@Getter
public class MovieResponse {
    private Long id;
    private String title;
    private Float rating;
    private String thumbnailUrl;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.rating = movie.getRating();
        this.thumbnailUrl = movie.getThumbnailUrl();
    }
}
