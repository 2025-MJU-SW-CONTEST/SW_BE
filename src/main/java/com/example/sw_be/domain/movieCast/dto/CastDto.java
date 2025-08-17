package com.example.sw_be.domain.movieCast.dto;

import com.example.sw_be.domain.movieCast.entity.MovieCast;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CastDto {
    private String name;
    private String profile_path;
    private String character;

    public CastDto(MovieCast movieCast) {
        this.name = movieCast.getName();
        this.profile_path = movieCast.getProfile();
        this.character = movieCast.getCharacter();
    }
}