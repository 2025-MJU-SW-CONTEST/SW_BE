package com.example.sw_be.domain.movieCast.service;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movieCast.dto.MovieCastsResponse;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import org.apache.catalina.CredentialHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieCastService {
    @Value("${spring.tmdb.api.token}")
    private String token;

    private final WebClient webClient= WebClient.builder()
            .baseUrl("https://api.themoviedb.org/3")
            .defaultHeader("Authorization", "Bearer "+token)
            .build();

    public List<MovieCast> saveCasts(Long id) {
        List<MovieCast> casts= new ArrayList<>();
        MovieCastsResponse response = webClient
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

