package com.example.sw_be.domain.movieCast.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MovieCastsResponse {
    private Long movieId; // 영화 ID
    private List<CastDto> cast; // 배우 리스트

    public MovieCastsResponse(Long movieId, List<CastDto> castDtos) {
        this.movieId = movieId;
        this.cast = castDtos;
    }

}
