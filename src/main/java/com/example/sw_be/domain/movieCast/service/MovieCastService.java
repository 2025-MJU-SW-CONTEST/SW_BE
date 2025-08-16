package com.example.sw_be.domain.movieCast.service;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movieCast.dto.MovieCastsResponse;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.CredentialHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCastService {

    @Qualifier("tmdbClient")
    private final WebClient tmdbClient;

    public List<MovieCast> saveCasts(Long id) {
        List<MovieCast> casts= new ArrayList<>();
        MovieCastsResponse response = tmdbClient
                .get()
                .uri("/movie/{id}/credits", id)
                .retrieve()
                .bodyToMono(MovieCastsResponse.class)
                .block();

        if (response != null && response.getCast() != null) {
            for (MovieCastsResponse.CastDto dto : response.getCast()) {
                MovieCast cast = MovieCast.builder()
                        .name(dto.getName())
                        .character(dto.getCharacter())
                        .profile(dto.getProfile_path())
                        .build();

                casts.add(cast);
            }
        }
        return casts;
    }
}

