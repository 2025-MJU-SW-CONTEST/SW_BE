package com.example.sw_be.domain.movie.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class MovieApiResponse {
    private int page;
    private List<MovieDto> results;

    @Data
    public static class MovieDto {
        private Long id;
        private String title;
        private String overview;
        private Float vote_average;
        private String poster_path;
        private LocalDate release_date;
        private List<Long> genre_ids;
    }
}
