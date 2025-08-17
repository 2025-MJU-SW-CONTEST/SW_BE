package com.example.sw_be.domain.movieCast.dto;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import lombok.Data;
import java.util.List;

@Data
public class MovieCastsResponse {
    private Long movieId; // 영화 ID
    private List<CastDto> cast; // 배우 리스트

    public MovieCastsResponse(Long movieId, List<CastDto> castDtos) {
        this.movieId = movieId;
        this.cast = castDtos;
    }

    @Data
    public static class CastDto {
        private String name;
        private String profile_path;
        private String character;

        public CastDto(MovieCast movieCast) {
            this.name = movieCast.getName();
            this.profile_path = movieCast.getProfile();
            this.character = movieCast.getCharacter();
        }
    }

}
