package com.example.sw_be.domain.movieCast.service;

import com.example.sw_be.domain.movieCast.dto.MovieCastsResponse;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import com.example.sw_be.domain.movieCast.repository.MovieCastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCastService {

    @Qualifier("tmdbClient")
    private final WebClient tmdbClient;
    private final MovieCastRepository movieCastRepository;

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

    public MovieCastsResponse getMovieCasts(Long movieId) {
        List<MovieCast> movieCastList = movieCastRepository.findByMovieId(movieId);
        List<MovieCastsResponse.CastDto> castDtos = new ArrayList<>();
        for (MovieCast movieCast : movieCastList) {
            MovieCastsResponse.CastDto castDto = new MovieCastsResponse.CastDto(movieCast);
            castDtos.add(castDto);
        }
        return new MovieCastsResponse(movieId, castDtos);
    }
}

