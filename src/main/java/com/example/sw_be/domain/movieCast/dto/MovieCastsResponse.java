package com.example.sw_be.domain.movieCast.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieCastsResponse {
    private Long id; // 영화 ID
    private List<CastDto> cast; // 배우 리스트

    @Data
    public static class CastDto {
        private String name;
        private String profile_path;
        private String character;
    }
}
