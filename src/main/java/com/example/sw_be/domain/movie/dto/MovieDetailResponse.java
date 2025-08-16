package com.example.sw_be.domain.movie.dto;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MovieDetailResponse {
        private Long id;
        private String title;
        private String summary;
        private Float rating;
        private String thumbnailUrl;
        private LocalDate releaseDate;
        private List<String> genres;
        private List<MovieCast> movieCasts;

    public MovieDetailResponse(Movie movie) {
            this.id = movie.getId();
            this.title = movie.getTitle();
            this.summary = movie.getSummary();
            this.rating = movie.getRating();
            this.thumbnailUrl = movie.getThumbnailUrl();
            this.releaseDate = movie.getReleaseDate();
            this.genres = movie.getMovieGenres().stream()
                    .map(mg -> mg.getGenre().getName()) // 장르 이름
                    .toList();
            this.movieCasts= movie.getMovieCasts();
    }
}
